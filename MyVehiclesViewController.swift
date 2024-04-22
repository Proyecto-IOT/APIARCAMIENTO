//
//  MyVehiclesViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class MyVehiclesViewController: UIViewController {

    @IBOutlet weak var svMyVehicles: UIScrollView!
    @IBOutlet weak var ivVehicle: UIImageView!
    @IBOutlet weak var lblVehicle: UILabel!
    @IBOutlet weak var vAddVehicle: UIView!
    let colorLabel = UIColor.black
    let user = UserDefaults.standard
    let id: Int = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        vAddVehicle.layer.cornerRadius = 10
        
    }
    override func viewDidAppear(_ animated: Bool) {
        lblVehicle.alpha = 0
        ivVehicle.alpha = 1
        ivVehicle.image = UIImage(named: "loading")
        
        vehicle()
    }
    
    func vehicle(){
        
        DispatchQueue.main.async {
            for subview in self.svMyVehicles.subviews {
                if type(of: subview) == UIView.self{
                    subview.removeFromSuperview()

                }
            }
        }

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
                if let msg = json["msg"] as? String {
                    if msg == "No cuentas con vehiculos."{
                        DispatchQueue.main.async {
                            self.lblVehicle.alpha = 1
                            self.ivVehicle.alpha = 1
                            self.ivVehicle.image = UIImage(named: "car-empty")
                        }
                    }
                }
                if let data = json["data"] as? [[String: Any]] {
                    let spacing: CGFloat = 10
                    var totalHeight: CGFloat = spacing

                    DispatchQueue.main.async {
                        self.ivVehicle.alpha = 0
                        self.lblVehicle.alpha = 0

                        for (index, value) in data.enumerated() {
                            
                            let xPos: CGFloat = 20
                            let yPos: CGFloat = totalHeight
                            let width: CGFloat = self.svMyVehicles.frame.width - 40
                            let height: CGFloat = 170
                            
                            let miView = UIView(frame: CGRect(x: xPos, y: yPos, width: width, height: height))
                            miView.backgroundColor = UIColor(red: 243/255.0, green: 230/255.0, blue: 253/255.0, alpha: 1.0)
                            miView.layer.cornerRadius = 10
                            self.svMyVehicles.addSubview(miView)
                            
                            let marcaLabel = UILabel(frame: CGRect(x: 10, y: 10, width: width - 20, height: 20))
                            if let name = value["brand"] as? String {
                                marcaLabel.text = "Marca: " + name
                            } else {
                                marcaLabel.text = "No Name"
                            }
                            marcaLabel.textColor = self.colorLabel
                            marcaLabel.textAlignment = .left
                            miView.addSubview(marcaLabel)
                            
                            
                            let modeloLabel = UILabel(frame: CGRect(x: 10, y: 40, width: width - 20, height: 20))
                            if let modelo = value["model"] as? String {
                                modeloLabel.text = "Modelo: " + modelo
                            } else {
                                modeloLabel.text = "No Model"
                            }
                            modeloLabel.textColor = self.colorLabel
                            modeloLabel.textAlignment = .left
                            miView.addSubview(modeloLabel)
                            
                            let colorLabel = UILabel(frame: CGRect(x: 10, y: 70, width: width - 20, height: 20))
                            if let color = value["color"] as? String {
                                colorLabel.text = "Color: " + color
                            } else {
                                colorLabel.text = "No Color"
                            }
                            colorLabel.textColor = self.colorLabel
                            colorLabel.textAlignment = .left
                            miView.addSubview(colorLabel)
                            
                            let plateLabel = UILabel(frame: CGRect(x: 10, y: 100, width: width - 20, height: 20))
                            if let plate = value["license_plate"] as? String {
                                plateLabel.text = "Placa: " + plate
                            } else {
                                plateLabel.text = "No placa"
                            }
                            plateLabel.textColor = self.colorLabel
                            plateLabel.textAlignment = .left
                            miView.addSubview(plateLabel)
                            
                            
                            let btnDetalle = UIButton(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
                            if let ide = value["vehicle_id"] as? Int {
                                btnDetalle.tag = ide
                            }
                            btnDetalle.addTarget(self, action: #selector(self.irDetalle(sender: )), for: .touchUpInside)
                            btnDetalle.backgroundColor = UIColor(red: 141/255.0, green: 96/255.0, blue: 190/255.0, alpha: 1.0)
                            btnDetalle.setTitle("Eliminar vehículo", for: .normal)
                            miView.addSubview(btnDetalle)
                            
                            btnDetalle.translatesAutoresizingMaskIntoConstraints = false
                            NSLayoutConstraint.activate([
                                btnDetalle.trailingAnchor.constraint(equalTo: miView.trailingAnchor),
                                btnDetalle.bottomAnchor.constraint(equalTo: miView.bottomAnchor),
                                btnDetalle.widthAnchor.constraint(equalToConstant: 160),
                                btnDetalle.heightAnchor.constraint(equalToConstant: 40)
                                
                                
                                
                            ])
                            
                            let btnEdit = UIButton(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
                            if let ide = value["vehicle_id"] as? Int {
                                btnEdit.tag = ide
                            }
                            btnEdit.addTarget(self, action: #selector(self.edit(sender: )), for: .touchUpInside)
                            btnEdit.backgroundColor = UIColor(red: 141/255.0, green: 96/255.0, blue: 190/255.0, alpha: 1.0)
                            btnEdit.setTitle("Editar vehículo", for: .normal)
                            miView.addSubview(btnEdit)
                            btnEdit.translatesAutoresizingMaskIntoConstraints = false
                            NSLayoutConstraint.activate([
                                btnEdit.trailingAnchor.constraint(equalTo: btnDetalle.leadingAnchor, constant: -10),
                                btnEdit.bottomAnchor.constraint(equalTo: miView.bottomAnchor),
                                btnEdit.widthAnchor.constraint(equalToConstant: 160),
                                btnEdit.heightAnchor.constraint(equalToConstant: 40)
                            ])


                            totalHeight += height + spacing
                        }
                        
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
    
    @objc func irDetalle(sender: UIButton)
        {
            let tag = sender.tag
            let mensaje = UIAlertController(title: "SEGURO?", message: "¿Desea eliminar el carro?", preferredStyle: .alert)
            
            let ok = UIAlertAction(title: "ELIMINAR", style: .default){ (action) in
                self.delete(tag: tag)
                
            }
            let no = UIAlertAction(title: "CANCELAR", style: .default){ (action) in
                
            }
            self.present(mensaje, animated: true)
            mensaje.addAction(ok)
            mensaje.addAction(no)

            
        }
    @objc func edit(sender: UIButton)
        {
            let tag = sender.tag
            let mensaje = UIAlertController(title: "SEGURO?", message: "¿Desea editar el carro?", preferredStyle: .alert)
            
            let ok = UIAlertAction(title: "Editar", style: .default){ (action) in
                DispatchQueue.main.async {
                    
                    self.performSegue(withIdentifier: "sgEdit", sender: tag)
                }
                
            }
            let no = UIAlertAction(title: "Cancelar", style: .default){ (action) in
                
            }
            self.present(mensaje, animated: true)
            mensaje.addAction(ok)
            mensaje.addAction(no)

            
        }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "sgEdit" {
            if let spot = sender as? Int {
                if let destinationVC = segue.destination as? NewVehicleViewController {
                    destinationVC.spot = spot
                }
            }
        }
    }
    
    func delete(tag: Int){
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "vehicle/delete/\(tag)")!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "DELETE"
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
                let result = json["result"]
                if result as! Int == 1{
                    self.vehicle()
                }
                
            } catch {
                print("Error al analizar la respuesta JSON:", error)
            }



        }.resume()
    }
}
