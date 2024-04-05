//
//  MyVehiclesViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class MyVehiclesViewController: UIViewController {

    @IBOutlet weak var vAddVehicle: UIView!
    @IBOutlet weak var svMyVehicles: UIScrollView!
    
    let user = UserDefaults.standard

    override func viewDidLoad() {
        super.viewDidLoad()

        vAddVehicle.layer.cornerRadius = 10
        

    }
    override func viewDidAppear(_ animated: Bool) {
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
                print(json)
                if let data = json["data"] as? [[String: Any]] {
                    let spacing: CGFloat = 10 // Espacio entre las vistas
                    var totalHeight: CGFloat = spacing // Inicializamos con el espacio superior

                    DispatchQueue.main.async {
                        for (index, value) in data.enumerated() {
                            print(value)
                            
                            let xPos: CGFloat = 20
                            let yPos: CGFloat = totalHeight // Posición vertical de la vista
                            let width: CGFloat = self.svMyVehicles.frame.width - 40
                            let height: CGFloat = 130
                            
                            let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                            miView.backgroundColor = UIColor(red: 141/255.0, green: 96/255.0, blue: 159/255.0, alpha: 1.0)
                            self.svMyVehicles.addSubview(miView)
                            
                            let marcaLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                            if let name = value["brand"] as? String {
                                marcaLabel.text = "Marca: " + name
                            } else {
                                marcaLabel.text = "No Name"
                            }
                            marcaLabel.textColor = UIColor.white
                            marcaLabel.textAlignment = .left
                            miView.addSubview(marcaLabel)
                            
                            
                            let modeloLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                            if let modelo = value["model"] as? String {
                                modeloLabel.text = "Modelo: " + modelo
                            } else {
                                modeloLabel.text = "No Model"
                            }
                            modeloLabel.textColor = UIColor.white
                            modeloLabel.textAlignment = .left
                            miView.addSubview(modeloLabel)
                            
                            let colorLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))
                            if let color = value["color"] as? String {
                                colorLabel.text = "Color: " + color
                            } else {
                                colorLabel.text = "No Color"
                            }
                            colorLabel.textColor = UIColor.white
                            colorLabel.textAlignment = .left
                            miView.addSubview(colorLabel)
                            
                            let plateLabel = UILabel(frame: CGRect(x: 10, y: 100, width: width - 20, height: 20))
                            if let plate = value["license_plate"] as? String {
                                plateLabel.text = "Placa: " + plate
                            } else {
                                plateLabel.text = "No placa"
                            }
                            plateLabel.textColor = UIColor.white
                            plateLabel.textAlignment = .left
                            miView.addSubview(plateLabel)
                            
                            // Actualiza la altura total con el espacio para la siguiente vista
                            totalHeight += height + spacing
                        }
                        
                        // Ajusta el contenido del scrollView
                        self.svMyVehicles.contentSize = CGSize(width: self.svMyVehicles.frame.width, height: totalHeight)
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
