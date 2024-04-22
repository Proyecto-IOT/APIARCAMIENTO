//
//  LoginViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfPass: UITextField!
    @IBOutlet weak var btnSignin: UIButton!
    @IBOutlet weak var btnsignup: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        btnsignup.layer.cornerRadius = 10
        btnSignin.layer.cornerRadius = 10
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(hideKeyboard))
              view.addGestureRecognizer(tapGesture)    }
    @IBAction func login() {
        btnSignin.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/login")!
        let datos: [String: Any] = [
            "email": tfEmail.text!,
            "password": tfPass.text!
        ]
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "POST"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue("ngrok-skip-browser-warning", forHTTPHeaderField: "true")

        
        do {
            solicitud.httpBody = try JSONSerialization.data(withJSONObject: datos, options: .prettyPrinted)
        } catch let error {
            DispatchQueue.main.async {
                let mensaje = UIAlertController(title: "ERROR", message: "Error al realizar la solicitud: \(String(describing: error.localizedDescription)) ,Error desconocido", preferredStyle: .alert)
                
                let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                mensaje.addAction(ok)
                self.present(mensaje, animated: true)
            }

            return
        }
        
        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                DispatchQueue.main.async {
                    let mensaje = UIAlertController(title: "ERROR", message: "Error al realizar la solicitud: \(String(describing: error?.localizedDescription)) ,Error desconocido", preferredStyle: .alert)
                    
                    let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                    mensaje.addAction(ok)
                    self.present(mensaje, animated: true)
                }
                self.btnSignin.isEnabled = true
                return
            }
            
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                
                if let data = json["data"] as? [String: Any], let token = data["token"] as? String {
                    DispatchQueue.main.async {
                        UserDefaults.standard.set(token, forKey: "TOKEN")
                        self.performSegue(withIdentifier: "sgIndex", sender: nil)
                    }
                } else {
                    DispatchQueue.main.async {
                        var errorMessage = "Datos incorrectos"
                        if let mensa = json["msg"] as? String, mensa == "La cuenta no esta activa." {
                            errorMessage = mensa
                        }
                        
                        let mensaje = UIAlertController(title: "ERROR", message: errorMessage, preferredStyle: .alert)
                        let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                        mensaje.addAction(ok)
                        self.present(mensaje, animated: true)
                        self.btnSignin.isEnabled = true
                    }
                }
            } catch  {
                DispatchQueue.main.async {
                    let mensaje = UIAlertController(title: "ERROR", message: "Hubo un error al analizar la respuesta", preferredStyle: .alert)
                    
                    let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                    mensaje.addAction(ok)
                    self.present(mensaje, animated: true)
                    self.btnSignin.isEnabled = true
                }
            }


        }.resume()
        
    }
    @objc func hideKeyboard() {
            view.endEditing(true)
        }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            tfPass.resignFirstResponder()
            return true
        }

    
}
