//
//  MyVehiclesViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class MyVehiclesViewController: UIViewController {

    @IBOutlet weak var vAddVehicle: UIView!
    let user = UserDefaults.standard

    override func viewDidLoad() {
        super.viewDidLoad()

        vAddVehicle.layer.cornerRadius = 10
        
        userInfo()

    }
    
    func userInfo(){
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
                print("json", json)
                if let data = json["data"] as? [String: Any] {
                    for value in data {
                        print(value)
                        let miView = UIView(frame: CGRect(x: 100, y: 100, width: 200, height: 200))
                        miView.backgroundColor = UIColor.red
                        self.view.addSubview(miView)
                        let miLabel = UILabel(frame: CGRect(x: 10, y: 10, width: 180, height: 30))
                        miLabel.text = "Mi etiqueta"
                        miLabel.textColor = UIColor.white
                        miLabel.textAlignment = .left
                        miLabel.font = UIFont.systemFont(ofSize: 16)
                        miView.addSubview(miLabel)
                    }
                }
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }


        }.resume()

    }
    

    @IBAction func back() {
        dismiss(animated: true)
    }
    
}
