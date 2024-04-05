//
//  MyInformationViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class MyInformationViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource  {
    
    @IBOutlet weak var svMyInformation: UIScrollView!
    @IBOutlet weak var tfName: UITextField!
    @IBOutlet weak var tfApellidoPaterno: UITextField!
    @IBOutlet weak var tfApellidoMaterno: UITextField!
    @IBOutlet weak var pvGenero: UIPickerView!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var tfCorreo: UITextField!
    @IBOutlet weak var btnGuardarCambios: UIButton!
    var generos = ["Masculino", "Femenino", "39 tipos de gay"]
    let user = UserDefaults.standard
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.userInfo()
        pvGenero.dataSource = self
        pvGenero.delegate = self
    }
    
    func userInfo(){
        let user = UserDefaults.standard
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/user-info")!
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
                if let result = json["result"] as? Int {
                    
                    if result == 1 {
                        if let datos = json["data"] as? [String: Any] {
                            DispatchQueue.main.async {
                                if let name = datos["name"] as? String {
                                    self.tfName.text = name
                                }
                                if let last_name = datos["last_name"] as? String {
                                    self.tfApellidoPaterno.text = last_name
                                }
                                if let mother_surname = datos["mother_surname"] as? String {
                                    self.tfApellidoMaterno.text = mother_surname
                                }
                                
                                if let gender = datos["gender"] as? String {
                                    if let index = self.generos.firstIndex(of: gender) {
                                        self.pvGenero.selectRow(index, inComponent: 0, animated: false)
                                    }
                                }
                                if let email = datos["email"] as? String {
                                    self.tfCorreo.text = email
                                }
                                
                            }
                        }
                    } else {
                        //Mensaje
                    }
                }
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }
            
            
        }.resume()
        
    }
    
    
    override func viewDidAppear(_ animated: Bool) {
        svMyInformation.contentSize = CGSize(width: 0, height: btnGuardarCambios.frame.origin.y + btnGuardarCambios.frame.height + 10)
        btnGuardarCambios.layer.cornerRadius = 10
        
    }
    
    
    @IBAction func guardarCambios(_ sender: UIButton) {
        sender.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/update")!
        var estatus:Int = 0
        let selectedIndex = pvGenero.selectedRow(inComponent: 0)
        let textoSeleccionado = generos[selectedIndex]
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "name": tfName.text!,
            "last_name": tfApellidoPaterno.text!,
            "mother_surname": tfApellidoMaterno.text!,
            "gender": textoSeleccionado,
            "email": tfCorreo.text!,
            "password": tfPassword.text!
        ]
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "PUT"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue(token, forHTTPHeaderField: "Authorization")

        
        do {
            print("si?")
            solicitud.httpBody = try JSONSerialization.data(withJSONObject: datos, options: .prettyPrinted)
        } catch let error {
            print("Error al serializar los datos:", error.localizedDescription)
            return
        }
        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                sender.isEnabled = true
                return
            }
            if let httpResponse = respuesta as? HTTPURLResponse {
                print("Código de estado de la respuesta:", httpResponse.statusCode)
                estatus = httpResponse.statusCode
                print(estatus, httpResponse.statusCode)
            }
            DispatchQueue.main.async {
                if estatus >= 400{
                    sender.isEnabled = true
                }else if estatus >= 200 && estatus < 300{
                    print("no?")
                    self.dismiss(animated: true)
                }
            }

        }.resume()
        
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return generos.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String?
    {
        return generos[row]
    }
    

    @IBAction func Back() {
        dismiss(animated:true)
    }
}
