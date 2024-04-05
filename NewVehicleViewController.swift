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

    
    override func viewDidLoad() {
        super.viewDidLoad()

        btnAddVehicle.layer.cornerRadius = 10

    }
    override func viewDidAppear(_ animated: Bool) {
        svNewVehicle.contentSize = CGSize(width: 0, height: btnAddVehicle.frame.origin.y + btnAddVehicle.frame.height + 10)
    }
    
    

    @IBAction func addVehicle(_ sender: UIButton) {
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
            print("si?")
            solicitud.httpBody = try JSONSerialization.data(withJSONObject: datos, options: .prettyPrinted)
        } catch let error {
            print("Error al serializar los datos:", error.localizedDescription)
            return
        }
        
        let sesion = URLSession(configuration: .default)
        sesion.dataTask(with: solicitud) { datos, respuesta, error in
            guard let datos = datos, error == nil else {
                sender.isEnabled = true
                print("Error al realizar la solicitud:", error?.localizedDescription ?? "Error desconocido")
                
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
                    self.dismiss(animated: true)
                }
            }

        }.resume()    }
    
}
