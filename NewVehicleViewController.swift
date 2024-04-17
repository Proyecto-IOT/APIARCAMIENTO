//
//  NewVehicleViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class NewVehicleViewController: UIViewController {

    @IBOutlet weak var svNewVehicle: UIScrollView!
    @IBOutlet weak var tfPlate: UITextField!
    @IBOutlet weak var tfBrand: UITextField!
    @IBOutlet weak var tfModel: UITextField!
    @IBOutlet weak var tfColor: UITextField!
    @IBOutlet weak var btnAddVehicle: UIButton!
    let user = UserDefaults.standard
    var spot: Int = 0
    let colorLabel = UIColor.black


    
    override func viewDidLoad() {
        super.viewDidLoad()

        btnAddVehicle.layer.cornerRadius = 10

    }
    override func viewDidAppear(_ animated: Bool) {
        svNewVehicle.contentSize = CGSize(width: 0, height: btnAddVehicle.frame.origin.y + btnAddVehicle.frame.height + 10)
        
        if spot != 0 {
            print("diferente")
            edit()
        }
    }
    
    func edit(){
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "vehicle/search/\(spot)")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "GET"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue(token, forHTTPHeaderField: "Authorization")

        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                return
            }
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                print(json)
                if let msg = json["msg"] as? String {
                    if msg == "No cuentas con vehiculos."{
                        
                    }
                }
                if let data = json["data"] as? [String: Any] {
                    
                    print(data)
                    
                    DispatchQueue.main.async {
                        
                        
                        if let name = data["brand"] as? String {
                            print("sipuedo")
                            self.tfBrand.text = name
                        } else {
                        }
                        
                        if let modelo = data["model"] as? String {
                            self.tfModel.text = modelo

                        } else {
                        }
                        
                        if let color = data["color"] as? String {
                            self.tfColor.text = color
                        } else {
                        }
                        
                        if let plate = data["license_plate"] as? String {
                            self.tfPlate.text = plate

                        } else {
                        }
                    }
                    
                }
                
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()
    }

    @IBAction func addVehicle(_ sender: UIButton) {
        if self.spot != 0 {
            editFunc(sender: sender)
            return;
        }
        print("hola")
        sender.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "vehicle/register")!
        var estatus:Int = 0
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "license_plate": tfPlate.text!,
            //"user_id": "2",
            "brand": tfBrand.text!,
            "model": tfModel.text!,
            "color": tfColor.text!,
        ]
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "POST"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue(token, forHTTPHeaderField: "Authorization")

        
        do {
            solicitud.httpBody = try JSONSerialization.data(withJSONObject: datos, options: .prettyPrinted)
        } catch let error {
            print("Error al serializar los datos:", error.localizedDescription)
            return
        }
        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                DispatchQueue.main.async {
                    self.btnAddVehicle.isEnabled = true
                }
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                
                return
            }
            if let httpResponse = respuesta as? HTTPURLResponse {
                print("Código de estado de la respuesta:", httpResponse.statusCode)
                estatus = httpResponse.statusCode
                print(estatus, httpResponse.statusCode)
            }
            var msg = ""
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                if let errors = json["errors"] as? [String: Any] {
                    for (_, value) in errors {
                        if let errorMessages = value as? [String] {
                            let formattedErrors = errorMessages.joined(separator: "\n")
                            msg += "\(formattedErrors)\n"
                            print("Error: \(formattedErrors)")
                        }
                    }
                } else if let mensaje = json["msg"] as? String {
                    msg = mensaje
                }
            } catch {
                print("Error al procesar la respuesta JSON")
            }

            if let httpResponse = respuesta as? HTTPURLResponse {
                estatus = httpResponse.statusCode
                DispatchQueue.main.async {
                    print("Estatus:", estatus)
                    DispatchQueue.main.async {
                        if estatus >= 200 && estatus < 300{
                            print("Entra")
                            let mensaje = UIAlertController(title: "EXITO", message: "Se ha agregado el vehículo", preferredStyle: .alert)
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                self.dismiss(animated: true)
                            }
                            mensaje.addAction(ok)
                            self.present(mensaje, animated: true)
                            
                        }else{
                            self.btnAddVehicle.isEnabled = true
                            let mensaje = UIAlertController(title: "ERROR", message: "\(msg)", preferredStyle: .alert)
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                            mensaje.addAction(ok)
                            self.present(mensaje, animated: true)
                        }
                    }
                }
            }

        }.resume()    }
    
    @IBAction func back() {
        dismiss(animated:true)

    }
    func editFunc(sender:UIButton){
        print("hola")
        sender.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "vehicle/update/\(self.spot)")!
        var estatus:Int = 0
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "license_plate": tfPlate.text!,
            //"user_id": "2",
            "brand": tfBrand.text!,
            "model": tfModel.text!,
            "color": tfColor.text!,
        ]
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "PUT"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue(token, forHTTPHeaderField: "Authorization")

        
        do {
            solicitud.httpBody = try JSONSerialization.data(withJSONObject: datos, options: .prettyPrinted)
        } catch let error {
            print("Error al serializar los datos:", error.localizedDescription)
            return
        }
        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                DispatchQueue.main.async {
                    self.btnAddVehicle.isEnabled = true
                }
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                
                return
            }
            if let httpResponse = respuesta as? HTTPURLResponse {
                print("Código de estado de la respuesta:", httpResponse.statusCode)
                estatus = httpResponse.statusCode
                print(estatus, httpResponse.statusCode)
            }
            var msg = ""
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                if let errors = json["errors"] as? [String: Any] {
                    for (_, value) in errors {
                        if let errorMessages = value as? [String] {
                            let formattedErrors = errorMessages.joined(separator: "\n")
                            msg += "\(formattedErrors)\n"
                            print("Error: \(formattedErrors)")
                        }
                    }
                } else if let mensaje = json["msg"] as? String {
                    msg = mensaje
                }
            } catch {
                print("Error al procesar la respuesta JSON")
            }

            if let httpResponse = respuesta as? HTTPURLResponse {
                estatus = httpResponse.statusCode
                DispatchQueue.main.async {
                    print("Estatus:", estatus)
                    DispatchQueue.main.async {
                        if estatus >= 200 && estatus < 300{
                            print("Entra")
                            let mensaje = UIAlertController(title: "EXITO", message: "Se ha agregado el vehículo", preferredStyle: .alert)
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                self.dismiss(animated: true)
                            }
                            mensaje.addAction(ok)
                            self.present(mensaje, animated: true)
                            
                        }else{
                            self.btnAddVehicle.isEnabled = true
                            let mensaje = UIAlertController(title: "ERROR", message: "\(msg)", preferredStyle: .alert)
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                            mensaje.addAction(ok)
                            self.present(mensaje, animated: true)
                        }
                    }
                }
            }

        }.resume()
    }
}
