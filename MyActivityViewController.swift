//
//  MyActivityViewController.swift
//  APIARCAMIENTO
//
//  Created by Federico Mireles on 09/04/24.
//

import UIKit
import Foundation


class MyActivityViewController: UIViewController {

    @IBOutlet var svInOut: UIScrollView!
    @IBOutlet var svMyActivity: UIScrollView!
    @IBOutlet weak var lblSinRegistros: UILabel!
    @IBOutlet weak var ivEmpty: UIImageView!
    @IBOutlet weak var ivInOut: UIImageView!
    @IBOutlet weak var lblInOut: UILabel!
    var vieww: UIView!
    let user = UserDefaults.standard
    let colorLabel = UIColor.black
    var timer: Timer?

    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    override func viewDidAppear(_ animated: Bool) {
        lblSinRegistros.alpha = 0
        ivEmpty.alpha = 1
        ivEmpty.image = UIImage(named: "loading")
        
        lblInOut.alpha = 0
        ivInOut.alpha = 1
        ivInOut.image = UIImage(named: "loading")
        self.userActivity()
        self.inOut()
        self.timer = Timer.scheduledTimer(withTimeInterval: 10.0, repeats: true) { timer in
            self.lblSinRegistros.alpha = 0
            self.ivEmpty.alpha = 1
            self.ivEmpty.image = UIImage(named: "loading")
            
            self.lblInOut.alpha = 0
            self.ivInOut.alpha = 1
            self.ivInOut.image = UIImage(named: "loading")
            self.userActivity()
            self.inOut()
        }
    }
    override func viewDidDisappear(_ animated: Bool) {
        self.timer?.invalidate()
        self.timer = nil
    }
    
    func userActivity(){
        DispatchQueue.main.async {
            for subview in self.svMyActivity.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/activitypark")!
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
                
                if let msg = json["msg"] as? String {
                    if msg == "No se encuentra con un historial"{
                        DispatchQueue.main.async {
                            self.lblSinRegistros.alpha = 1
                            self.ivEmpty.image = UIImage(named: "empty")
                        }
                    }
                }
               
                if let data = json["data"] as? [[String: Any]] {
                    let spacing: CGFloat = 10
                    var totalHeight: CGFloat = spacing
                    DispatchQueue.main.async {
                        self.ivEmpty.alpha = 0
                        self.lblSinRegistros.alpha = 0
                        for (index, value) in data.enumerated() {
                            
                            let xPos: CGFloat = 20
                            let yPos: CGFloat = totalHeight
                            let width: CGFloat = self.svMyActivity.frame.width - 40
                            let height: CGFloat = 130
                            
                            let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                            miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                            miView.layer.cornerRadius = 10
                            self.svMyActivity.addSubview(miView)
                            
                            let marcaLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                            let brandLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))
                            
                            if let name = value["parking_id"] as? Int {
                                marcaLabel.text = "Lugar: \(name)"
                            } else {
                                marcaLabel.text = "No Spot"
                            }
                            marcaLabel.textColor = self.colorLabel
                            marcaLabel.textAlignment = .left
                            miView.addSubview(marcaLabel)
                            
                            
                            let modeloLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                            
                            
                            
                            if let vehicle = value["vehicle"] as? [String: Any] {
                                if let modelo = vehicle["model"] as? String {
                                    modeloLabel.text = "Modelo: " + modelo
                                } else {
                                    modeloLabel.text = "No Model"
                                }
                                if let brand = vehicle["brand"] as? String {
                                    brandLabel.text = "Marca: " + brand
                                } else {
                                    brandLabel.text = "No placa"
                                }
                                
                            }

                            
                            
                            
                            let dateLabel = UILabel(frame: CGRect(x: 10, y: 100, width: width - 20, height: 20))
                            if let dateString = value["created_at"] as? String {
                                let newString = String(dateString.dropLast(17))
                                dateLabel.text = "Fecha:  \(newString)"
                            } else {
                                dateLabel.text = "No Date"
                            }
                            
                            dateLabel.textColor = self.colorLabel
                            dateLabel.textAlignment = .left
                            miView.addSubview(dateLabel)
                            
                            brandLabel.textColor = self.colorLabel
                            brandLabel.textAlignment = .left
                            miView.addSubview(brandLabel)
                            
                            modeloLabel.textColor = self.colorLabel
                            modeloLabel.textAlignment = .left
                            miView.addSubview(modeloLabel)
                            
                            totalHeight += height + spacing
                        }
                        
                        self.svMyActivity.contentSize = CGSize(width: self.svMyActivity
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
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }
    
    func inOut(){

        DispatchQueue.main.async {
            for subview in self.svInOut.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }
            
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "user/activitylogs")!
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
                if let msg = json["msg"] as? String {
                    if msg == "No se encuentra con un historial"{
                        DispatchQueue.main.async {
                            self.lblInOut.alpha = 1
                            self.ivInOut.image = UIImage(named: "empty")
                        }
                    }
                }
                
                if let data = json["data"] as? [[String: Any]] {
                    let spacing: CGFloat = 10
                    var totalHeight: CGFloat = spacing
                    DispatchQueue.main.async {
                        self.ivInOut.alpha = 0
                        self.lblInOut.alpha = 0
                        for (index, value) in data.enumerated() {
                            
                            let xPos: CGFloat = 20
                            let yPos: CGFloat = totalHeight
                            let width: CGFloat = self.svInOut.frame.width - 40
                            let height: CGFloat = 100
                            
                            let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                            miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                            miView.layer.cornerRadius = 10
                            self.svInOut.addSubview(miView)
                            
                            let marcaLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                            var imageView = UIImageView(image: UIImage(named: "question-car"))

                            if let name = value["action"] as? String {
                                if name == "Entrada"{
                                    imageView = UIImageView(image: UIImage(named: "log-in"))
                                }else{
                                    imageView = UIImageView(image: UIImage(named: "logout"))
                                }
                                
                                marcaLabel.text = "Acción: " + name
                            } else {
                                marcaLabel.text = "No Activity"
                            }
                            
                            marcaLabel.textColor = self.colorLabel
                            marcaLabel.textAlignment = .left
                            miView.addSubview(marcaLabel)
                            
                            
                            
                            let dateLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                            
                            let timeLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))
                            
                            if let dateString = value["created_at"] as? String {
                                let date = String(dateString.dropLast(17))
                                dateLabel.text = "Fecha:  \(date)"
                                let time = String(dateString.dropFirst(11))
                                
                                
                                let dateFormatter = DateFormatter()
                                dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
                                
                                dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
                                if let utcDate = dateFormatter.date(from:dateString) {
                                    dateFormatter.timeZone = TimeZone(abbreviation: "CST")
                                    dateFormatter.dateFormat = "h:mm:ss a"
                                    
                                    let mexicoDateStr = dateFormatter.string(from: utcDate)
                                    timeLabel.text = "Hora:  \(mexicoDateStr)"
                                    
                                }
                            } else {
                                dateLabel.text = "No Time"
                            }
                            
                            dateLabel.textColor = self.colorLabel
                            dateLabel.textAlignment = .left
                            miView.addSubview(dateLabel)
                            
                            timeLabel.textColor = self.colorLabel
                            timeLabel.textAlignment = .left
                            miView.addSubview(timeLabel)
                            
                            
                            
                            imageView.frame = CGRect(x: 0, y: 0, width: 100, height: 100)

                            imageView.autoresizingMask = [.flexibleTopMargin, .flexibleLeftMargin]
                            
                            miView.addSubview(imageView)
                            
                            imageView.frame.origin = CGPoint(x: miView.frame.width - imageView.frame.width, y: miView.frame.height - imageView.frame.height)
                            
                            totalHeight += height + spacing
                        }
                        
                        
                        
                        self.svInOut.contentSize = CGSize(width: self.svInOut
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
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()

    }

}
