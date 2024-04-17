//
//  ProfileViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class ProfileViewController: UIViewController {

    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var vDatos: UIView!
    @IBOutlet var ivProfile: UIImageView!
    let user = UserDefaults.standard
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //user.set("sadasd", forKey: "TOKEN")

        vDatos.layer.cornerRadius = 18
        vDatos.layer.maskedCorners = [.layerMinXMaxYCorner, .layerMaxXMaxYCorner]
    }
    override func viewDidAppear(_ animated: Bool) {
        self.userInfo()
    }
    
    func userInfo(){
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
                DispatchQueue.main.async {
                    print("FALLO")
                    let mensaje = UIAlertController(title: "ERROR", message: "Error al realizar la solicitud: \(String(describing: error?.localizedDescription)) ,Error desconocido", preferredStyle: .alert)
                    
                    let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                    mensaje.addAction(ok)
                    self.present(mensaje, animated: true)
                }
                return
            }
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                print(json)
                if let result = json["result"] as? Int {
                    if result == 1 {
                        
                        
                        
                        if let datos = json["data"] as? [String: Any] {
                            if let name = datos["name"] as? String {
                                DispatchQueue.main.async {self.lblName.text = name}
                            }
                            if let name = datos["gender"] as? String {
                                DispatchQueue.main.async {

                                    if name == "Masculino"{
                                        self.ivProfile.image = UIImage(named: "man")
                                        
                                    }else if name == "Femenino"{
                                        self.ivProfile.image = UIImage(named: "woman")
                                    }else{
                                        self.ivProfile.image = UIImage(named: "alien")
                                    }

                                }
                            }
                        }
                    } else {
                        DispatchQueue.main.async {self.lblName.text = "..."}
                    }
                }
                else if let result = json["message"] as? String {
                    if result == "Unauthenticated." {
                        
                        DispatchQueue.main.async {
                            let mensaje = UIAlertController(title: "INFO", message: "Tu sesión ha caducado", preferredStyle: .alert)
                            
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                self.user.set(nil, forKey: "TOKEN")
                                    
                            }
                            self.present(mensaje, animated: true)
                            mensaje.addAction(ok)
                        }
                    }
                }
            } catch {
                DispatchQueue.main.async {
                    let mensaje = UIAlertController(title: "INFO", message: "Error al analizar la respuesta", preferredStyle: .alert)
                    
                    let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                    mensaje.addAction(ok)
                    self.present(mensaje, animated: true)
                }

            }


        }.resume()

    }
    

    @IBAction func logOut(_ sender: UIButton) {
        sender.isEnabled = false
        let defaults = UserDefaults.standard
        let urlbase = defaults.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/logout")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "POST"
        solicitud.setValue("application/json", forHTTPHeaderField: "Content-Type")
        solicitud.setValue("application/json", forHTTPHeaderField: "Accept")
        solicitud.setValue(token, forHTTPHeaderField: "Authorization")

        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                sender.isEnabled = true
                return
            }
            
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                print(json)
                let result = json["result"]
                DispatchQueue.main.async {
                    if result as! Int == 1{
                        let mensaje = UIAlertController(title: "BYE :(", message: "Cuenta cerrada", preferredStyle: .alert)
                        
                        let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                            self.user.set(nil, forKey: "TOKEN")
                            self.performSegue(withIdentifier: "sgLogin", sender: nil)
                        }
                        self.present(mensaje, animated: true)
                        mensaje.addAction(ok)
                    }else{
                        sender.isEnabled = true
                        let mensaje = UIAlertController(title: "ERROR", message: "Usuario no encontrado.", preferredStyle: .alert)
                        
                        let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                        mensaje.addAction(ok)
                    }
                    
                }
                
            } catch {
                print("Error al analizar la respuesta JSON:", error)
                sender.isEnabled = true
            }


        }.resume()

    }
    
}
    


