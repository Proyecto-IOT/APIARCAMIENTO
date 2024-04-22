//
//  RegisterViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class RegisterViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
        

    @IBOutlet weak var tfName: UITextField!
    @IBOutlet weak var tfApellidoPaterno: UITextField!
    @IBOutlet weak var tfApellidoMaterno: UITextField!
    @IBOutlet weak var pvGenero: UIPickerView!
    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var svRegister: UIScrollView!
    @IBOutlet weak var btnSignup: UIButton!
    @IBOutlet weak var btnIoTengoCuenta: UIButton!
    @IBOutlet weak var lblCreditos: UILabel!
    var generos = ["Masculino", "Femenino", "39 tipos de gays"]

    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        btnSignup.layer.cornerRadius = 10
        pvGenero.dataSource = self
        pvGenero.delegate = self
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(hideKeyboard))
              view.addGestureRecognizer(tapGesture)
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        svRegister.contentSize = CGSize(width: 0, height: btnIoTengoCuenta.frame.origin.y + btnIoTengoCuenta.frame.height + 10)
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



    @IBAction func registrarse() {
        btnSignup.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/register")!
        var estatus:Int = 0
        let selectedIndex = pvGenero.selectedRow(inComponent: 0)
        let textoSeleccionado = generos[selectedIndex]
        
        let datos: [String: Any] = [
            "name": tfName.text!,
            "last_name": tfApellidoPaterno.text!,
            "mother_surname": tfApellidoMaterno.text!,
            "gender": textoSeleccionado,
            "email": tfEmail.text!,
            "password": tfPassword.text!
        ]
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "POST"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
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
                self.btnSignup.isEnabled = true
                return
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
                    DispatchQueue.main.async {
                        if estatus >= 200 && estatus < 300{
                            let mensaje = UIAlertController(title: "EXITO", message: "Te has registrado con exito, activa tu cuenta en el correo mandado", preferredStyle: .alert)
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                self.performSegue(withIdentifier: "sgSingUp", sender: nil)
                            }
                            mensaje.addAction(ok)
                            self.present(mensaje, animated: true)
                            
                        }else{
                            self.btnSignup.isEnabled = true
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
    
    @objc func hideKeyboard() {
            view.endEditing(true)
        }
    
        
}

