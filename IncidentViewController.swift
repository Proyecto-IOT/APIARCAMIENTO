//
//  IncidentViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class IncidentViewController: UIViewController {

    @IBOutlet weak var ivHumo: UIImageView!
    @IBOutlet weak var lblHumo: UILabel!
    @IBOutlet weak var svHumo: UIScrollView!
    
    @IBOutlet weak var ivRuido: UIImageView!
    @IBOutlet weak var svRuido: UIScrollView!
    @IBOutlet weak var lblRuido: UILabel!
    var vieww: UIView!
    let user = UserDefaults.standard
    let colorLabel = UIColor.black
    var timer: Timer?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    override func viewDidAppear(_ animated: Bool) {
        lblHumo.alpha = 0
        ivHumo.alpha = 1
        ivHumo.image = UIImage(named: "loading")
        
        lblRuido.alpha = 0
        ivRuido.alpha = 1
        ivRuido.image = UIImage(named: "loading")
        self.humo()
        self.ruido()
        self.timer = Timer.scheduledTimer(withTimeInterval: 60.0, repeats: true) { timer in
            self.lblHumo.alpha = 0
            self.ivHumo.alpha = 1
            self.ivHumo.image = UIImage(named: "loading")
            
            self.lblRuido.alpha = 0
            self.ivRuido.alpha = 1
            self.ivRuido.image = UIImage(named: "loading")
            self.humo()
            self.ruido()
        }
    }

    @IBAction func back() {
        self.timer?.invalidate()
        self.timer = nil
        dismiss(animated:true)
    }
    func humo(){
        DispatchQueue.main.async {
            for subview in self.svHumo.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "arduino/humo")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "id": 1,
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
                print(json)
                if let msg = json["msg"] as? String {
                    if msg == "No se encuentra con un historial"{
                        DispatchQueue.main.async {
                            self.lblHumo.alpha = 1
                            self.ivHumo.image = UIImage(named: "empty")
                        }
                    }
                }
                
                if let data = json["data"] as? [String: Any] {
                    if let dataa = data["Data"] as? [[String: Any]] {

                        let spacing: CGFloat = 10
                        var totalHeight: CGFloat = spacing
                        DispatchQueue.main.async {
                            self.ivHumo.alpha = 0
                            self.lblHumo.alpha = 0
                            for (index, value) in dataa.enumerated() {
                                
                                
                                let xPos: CGFloat = 20
                                let yPos: CGFloat = totalHeight
                                let width: CGFloat = self.svHumo.frame.width - 40
                                let height: CGFloat = 130
                                
                                let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                                miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                                miView.layer.cornerRadius = 10
                                self.svHumo.addSubview(miView)
                                
                                let datoLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                               
                                
                                if let dato = value["Dato"] as? String {
                                    datoLabel.text = "Porcentaje de humo: \(dato)%"
                                } else {
                                    datoLabel.text = "No Spot"
                                }
                                datoLabel.textColor = self.colorLabel
                                datoLabel.textAlignment = .left
                                miView.addSubview(datoLabel)
                                
                                let dateLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                                if let dateString = value["Fecha"] as? String {
                                    let newString = String(dateString.dropLast(6))
                                    dateLabel.text = "Fecha:  \(newString)"
                                } else {
                                    dateLabel.text = "No Date"
                                }
                                
                                
                                let hourLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))

                                if let hourString = value["Fecha"] as? String {
                                    let newString = String(hourString.dropFirst(11))
                                    hourLabel.text = "Hora:  \(newString)"
                                } else {
                                    hourLabel.text = "No Date"
                                }
                                
                                hourLabel.textColor = self.colorLabel
                                hourLabel.textAlignment = .left
                                miView.addSubview(hourLabel)
                                
                                dateLabel.textColor = self.colorLabel
                                dateLabel.textAlignment = .left
                                miView.addSubview(dateLabel)
                                
                                
                                dateLabel.textColor = self.colorLabel
                                dateLabel.textAlignment = .left
                                miView.addSubview(dateLabel)
                              
                                var imageView = UIImageView(image: UIImage(named: "smoke"))
                                
                                imageView.frame = CGRect(x: 0, y: 0, width: 100, height: 100)

                                imageView.autoresizingMask = [.flexibleTopMargin, .flexibleLeftMargin]
                                
                                miView.addSubview(imageView)
                                
                                imageView.frame.origin = CGPoint(x: miView.frame.width - imageView.frame.width, y: miView.frame.height - imageView.frame.height)
                                
                                totalHeight += height + spacing
                            }
                            
                            self.svHumo.contentSize = CGSize(width: self.svHumo
                                .frame.width, height: totalHeight)
                        }
                    }
                    else if let result = json["message"] as? String {
                        if result == "Unauthenticated" {
                            
                            DispatchQueue.main.async {
                                let mensaje = UIAlertController(title: "Info", message: "Tu sesión ha caducado", preferredStyle: .alert)
                                
                                let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                    self.user.set(nil, forKey: "TOKEN")
                                    self.performSegue(withIdentifier: "sgToken", sender: nil)
                                }
                                self.present(mensaje, animated: true)
                                mensaje.addAction(ok)
                            }
                        }
                    }
                }
            }catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }
    func ruido(){

        DispatchQueue.main.async {
            for subview in self.svRuido.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "arduino/ruido")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        let datos: [String: Any] = [
            "id": 1,
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
                
                if let msg = json["msg"] as? String {
                    if msg == "No se encuentra con un historial"{
                        DispatchQueue.main.async {
                            self.lblRuido.alpha = 1
                            self.ivRuido.image = UIImage(named: "empty")
                        }
                    }
                }
                
                if let data = json["data"] as? [String: Any] {
                    if let dataa = data["Data"] as? [[String: Any]] {

                        let spacing: CGFloat = 10
                        var totalHeight: CGFloat = spacing
                        DispatchQueue.main.async {
                            self.ivRuido.alpha = 0
                            self.lblRuido.alpha = 0
                            for (index, value) in dataa.enumerated() {
                                
                                
                                let xPos: CGFloat = 20
                                let yPos: CGFloat = totalHeight
                                let width: CGFloat = self.svRuido.frame.width - 40
                                let height: CGFloat = 130
                                
                                let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                                miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                                miView.layer.cornerRadius = 10
                                self.svRuido.addSubview(miView)
                                
                                let datoLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                               
                                
                                if let dato = value["Dato"] as? String {
                                    datoLabel.text = "Decibeles: \(dato)"
                                } else {
                                    datoLabel.text = "No Spot"
                                }
                                datoLabel.textColor = self.colorLabel
                                datoLabel.textAlignment = .left
                                miView.addSubview(datoLabel)
                                
                                let dateLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                                if let dateString = value["Fecha"] as? String {
                                    let newString = String(dateString.dropLast(6))
                                    dateLabel.text = "Fecha:  \(newString)"
                                } else {
                                    dateLabel.text = "No Date"
                                }
                                let hourLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))

                                if let hourString = value["Fecha"] as? String {
                                    let newString = String(hourString.dropFirst(11))
                                    hourLabel.text = "Hora:  \(newString)"
                                } else {
                                    hourLabel.text = "No Date"
                                }
                                
                                hourLabel.textColor = self.colorLabel
                                hourLabel.textAlignment = .left
                                miView.addSubview(hourLabel)
                                
                                dateLabel.textColor = self.colorLabel
                                dateLabel.textAlignment = .left
                                miView.addSubview(dateLabel)
                                
                                dateLabel.textColor = self.colorLabel
                                dateLabel.textAlignment = .left
                                miView.addSubview(dateLabel)
                              
                                var imageView = UIImageView(image: UIImage(named: "noise"))
                                
                                imageView.frame = CGRect(x: 0, y: 0, width: 100, height: 100)

                                imageView.autoresizingMask = [.flexibleTopMargin, .flexibleLeftMargin]
                                
                                miView.addSubview(imageView)
                                
                                imageView.frame.origin = CGPoint(x: miView.frame.width - imageView.frame.width, y: miView.frame.height - imageView.frame.height)
                                
                                totalHeight += height + spacing
                            }
                            
                            self.svRuido.contentSize = CGSize(width: self.svRuido
                                .frame.width, height: totalHeight)
                        }
                    }
                    else if let result = json["message"] as? String {
                        if result == "Unauthenticated" {
                            
                            DispatchQueue.main.async {
                                let mensaje = UIAlertController(title: "Info", message: "Tu sesión ha caducado", preferredStyle: .alert)
                                
                                let ok = UIAlertAction(title: "ACEPTAR", style: .default){ (action) in
                                    self.user.set(nil, forKey: "TOKEN")
                                    self.performSegue(withIdentifier: "sgToken", sender: nil)
                                }
                                self.present(mensaje, animated: true)
                                mensaje.addAction(ok)
                            }
                        }
                    }
                }
            }catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }
}
