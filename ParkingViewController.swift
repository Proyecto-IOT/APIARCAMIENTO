//
//  ParkingViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit
import Foundation

class ParkingViewController: UIViewController {

    @IBOutlet weak var svParking: UIScrollView!
    @IBOutlet var btnsParkingSpot: [UIButton]!
    @IBOutlet weak var vwSeccion2: UIView!
    let user = UserDefaults.standard
    @IBOutlet var ImvsCar: [UIImageView]!
    var timer: Timer? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        for car in ImvsCar{
            var ran = Int.random(in: 1..<5)
            switch(ran){
            case 1:
                car.image = UIImage(named: "bluecar")
                break;
            case 2:
                car.image = UIImage(named: "yellowcar")

                break;
            case 3:
                car.image = UIImage(named: "redcar")

                break;
            case 4:
                car.image = UIImage(named: "whitecar")

                break;
            default:
                break;
            }
        }
        self.timer = Timer.scheduledTimer(withTimeInterval: 10.0, repeats: true) { timer in
            self.loadParking()
        }

    }
    override func viewDidAppear(_ animated: Bool) {
        svParking.contentSize = CGSize(width: 0, height: vwSeccion2.frame.origin.y + vwSeccion2.frame.height + 10)
        loadParking()
    }
    

    @IBAction func back() {
        self.timer?.invalidate()
        self.timer = nil
        dismiss(animated:true)
    }
    
    
    @IBAction func refresh() {
        loadParking()
    }
    
    
    @IBAction func park(_ sender: UIButton) {
        let spot = sender.tag
        self.performSegue(withIdentifier: "sgParkSpot", sender: spot)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "sgParkSpot" {
            if let spot = sender as? Int {
                // Aquí puedes acceder al valor de 'spot'
                // y pasarlo al view controller de destino.
                if let destinationVC = segue.destination as? ParkSpotViewController {
                    // Supongamos que DestinationViewController tiene una propiedad 'spotNumber'
                    destinationVC.spot = spot
                }
            }
        }
    }

    
    func loadParking(){
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "arduino/refresh")!
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
                    for datos in data{
                        if let spotSend = datos["parking_id"] as? Int {
                            
                            print("hola")
                            if let isActive = datos["is_active"] as? Int {
                                if isActive == 1 {
                                    for car in self.ImvsCar {
                                        DispatchQueue.main.async {
                                            
                                            if car.tag == spotSend {
                                                car.alpha = 1
                                            }
                                            
                                        }
                                    }
                                    for btn in self.btnsParkingSpot {
                                        DispatchQueue.main.async {
                                            
                                            if btn.tag == spotSend {
                                                btn.alpha = 0
                                            }
                                        }
                                    }
                                }
                                else{
                                    DispatchQueue.main.async {
                                        
                                        for car in self.ImvsCar {
                                            car.alpha = 0
                                            
                                        }
                                        for btn in self.btnsParkingSpot {
                                            btn.alpha = 1
                                            
                                        }
                                    }

                                }
                                print("hola")
                                
                            }
                        }
                        
                    }
                    
                }
                else if let result = json["message"] as? String {
                    if result == "Unauthenticated" {
                        
                        DispatchQueue.main.async {
                            let mensaje = UIAlertController(title: "INFO", message: "Tu sesión ha caducado", preferredStyle: .alert)
                            
                            let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                self.user.set(nil, forKey: "TOKEN")
                                self.performSegue(withIdentifier: "sgToken", sender: nil)
                            }
                            self.present(mensaje, animated: true)
                            mensaje.addAction(ok)
                        }
                    }
                }
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }
}
