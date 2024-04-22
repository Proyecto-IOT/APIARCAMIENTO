//
//  ParkSpotViewController.swift
//  APIARCAMIENTO
//
//  Created by Federico Mireles on 15/04/24.
//

import UIKit

class ParkSpotViewController: UIViewController {
    
    @IBOutlet weak var ivLoading: UIImageView!
    @IBOutlet weak var svParkSpot: UIScrollView!
    @IBOutlet weak var lblParkSpot: UILabel!
    let colorLabel = UIColor.black
    let user = UserDefaults.standard
    var spot = 0
    
    override func viewDidLoad() {

        
        super.viewDidLoad()
        
    }
    override func viewDidAppear(_ animated: Bool) {
        lblParkSpot.alpha = 0
        ivLoading.alpha = 1
        ivLoading.image = UIImage(named: "loading")
        
        vehicle()
    }
    
    func vehicle(){
        
        DispatchQueue.main.async {
            for subview in self.svParkSpot.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }

        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "vehicle/search")!
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
                if let msg = json["msg"] as? String {
                    if msg == "No cuentas con vehiculos."{
                        DispatchQueue.main.async {
                            self.lblParkSpot.alpha = 1
                            self.ivLoading.alpha = 1
                            self.ivLoading.image = UIImage(named: "car-empty")
                        }
                    }
                }
                if let data = json["data"] as? [[String: Any]] {
                    let spacing: CGFloat = 10
                    var totalHeight: CGFloat = spacing

                    DispatchQueue.main.async {
                        self.ivLoading.alpha = 0
                        self.lblParkSpot.alpha = 0

                        for (index, value) in data.enumerated() {
                            
                            let xPos: CGFloat = 20
                            let yPos: CGFloat = totalHeight
                            let width: CGFloat = self.svParkSpot.frame.width - 40
                            let height: CGFloat = 130
                            
                            let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                            miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                            miView.layer.cornerRadius = 10
                            self.svParkSpot.addSubview(miView)
                            
                            let marcaLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                            if let name = value["brand"] as? String {
                                marcaLabel.text = "Marca: " + name
                            } else {
                                marcaLabel.text = "No Name"
                            }
                            marcaLabel.textColor = self.colorLabel
                            marcaLabel.textAlignment = .left
                            miView.addSubview(marcaLabel)
                            
                            
                            let modeloLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                            if let modelo = value["model"] as? String {
                                modeloLabel.text = "Modelo: " + modelo
                            } else {
                                modeloLabel.text = "No Model"
                            }
                            modeloLabel.textColor = self.colorLabel
                            modeloLabel.textAlignment = .left
                            miView.addSubview(modeloLabel)
                            
                            let colorLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))
                            if let color = value["color"] as? String {
                                colorLabel.text = "Color: " + color
                            } else {
                                colorLabel.text = "No Color"
                            }
                            colorLabel.textColor = self.colorLabel
                            colorLabel.textAlignment = .left
                            miView.addSubview(colorLabel)
                            
                            let plateLabel = UILabel(frame: CGRect(x: 10, y: 100, width: width - 20, height: 20))
                            if let plate = value["license_plate"] as? String {
                                plateLabel.text = "Placa: " + plate
                            } else {
                                plateLabel.text = "No placa"
                            }
                            plateLabel.textColor = self.colorLabel
                            plateLabel.textAlignment = .left
                            miView.addSubview(plateLabel)
                            
                            
                            let btnDetalle = UIButton(frame: CGRect(x: 0, y: 0, width: width, height: height))
                            if let ide = value["vehicle_id"] as? Int {
                                btnDetalle.tag = ide
                            }
                            btnDetalle.addTarget(self, action: #selector(self.seleccion(sender: )), for: .touchUpInside)
                            
                            miView.addSubview(btnDetalle)
                            
                            totalHeight += height + spacing
                        }
                        
                        self.svParkSpot.contentSize = CGSize(width: self.svParkSpot.frame.width, height: totalHeight)
                    }
                    
                }
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }
    @objc func seleccion(sender: UIButton)
        {
            let tag = sender.tag
            let mensaje = UIAlertController(title: "SEGURO?", message: "¿Desea seleccionar el vehículo?", preferredStyle: .alert)
            
            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                self.post(tag: tag)
                
            }
            let no = UIAlertAction(title: "CANCELAR", style: .default){ (action) in
                
            }
            self.present(mensaje, animated: true)
            mensaje.addAction(ok)
            mensaje.addAction(no)

            
        }
    func post(tag: Int){
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "arduino/park")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "id": self.spot,
            "vehicle_id": tag,
            "name": "Soriana",
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
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                return
            }
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                let result = json["result"]
                if result as! Int == 1{
                    DispatchQueue.main.async {
                        self.dismiss(animated:true)
                    }
                }
                
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()
    }
    
    @IBAction func back() {
        dismiss(animated:true)

    }
    


}
