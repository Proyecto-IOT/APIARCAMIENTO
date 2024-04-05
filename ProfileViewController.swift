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
    let user = UserDefaults.standard
    
    override func viewDidLoad() {
        super.viewDidLoad()

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
            print("HOla")
            do {
                let json = try JSONSerialization.jsonObject(with: datos) as! [String:Any]
                if let result = json["result"] as? Int {
                    if result == 1 {
                        if let datos = json["data"] as? [String: Any] {
                            if let name = datos["name"] as? String {
                                DispatchQueue.main.async {self.lblName.text = name}
                            }
                        }
                    } else {
                        DispatchQueue.main.async {self.lblName.text = "..."}
                    }
                }
            } catch {
                DispatchQueue.main.async {
                    let mensaje = UIAlertController(title: "ERROR", message: "Error al analizar la respuesta", preferredStyle: .alert)
                    
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
                if result as! Int == 1{
                    DispatchQueue.main.async {
                        let mensaje = UIAlertController(title: "Cuenta cerrada", message: "Error al realizar la solicitud: \(error?.localizedDescription) ,Error desconocido", preferredStyle: .alert)
                        
                        let ok = UIAlertAction(title: "ACEPTAR", style: .default)
                        mensaje.addAction(ok)
                        self.present(mensaje, animated: true)
                    }

                    self.user.set(nil, forKey: "TOKEN")
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "sgLogin", sender: nil)
                    }
                }else{
                    //MEnsaje
                }
            } catch {
                print("Error al analizar la respuesta JSON:", error)
                sender.isEnabled = true
            }


        }.resume()

    }
    
}
    


