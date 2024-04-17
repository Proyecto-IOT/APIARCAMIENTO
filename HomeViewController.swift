//
//  HomeViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class HomeViewController: UIViewController {

    @IBOutlet weak var svHome: UIScrollView!
    @IBOutlet weak var btnEstacionamiento: UIView!
    @IBOutlet weak var vIncidentes: UIView!
    @IBOutlet weak var vEntrada: UIView!
    @IBOutlet weak var vSalida: UIView!
    var longPressTimer: Timer?
    var secondsElapsed: Double = 0
    var impactFeedbackGenerator: UIImpactFeedbackGenerator?
    let user = UserDefaults.standard
    var verificador: Bool = false
    var ruta:String = ""
    


    override func viewDidLoad() {
        super.viewDidLoad()
        //user.set("sadasd", forKey: "TOKEN")

        vIncidentes.layer.cornerRadius = 10
        vSalida.layer.cornerRadius = 10
        vEntrada.layer.cornerRadius = 10
        btnEstacionamiento.layer.cornerRadius = 10
        impactFeedbackGenerator = UIImpactFeedbackGenerator(style: .medium)


        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        svHome.contentSize = CGSize(width: 0, height: btnEstacionamiento.frame.origin.y + btnEstacionamiento.frame.height + 10)
    }
   

    @IBAction func entrar() {
        ruta = "1"
        longPressTimer?.invalidate()
           
        // Reiniciar el contador de tiempo
        secondsElapsed = 0
        
        longPressTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(handleLongPress(_:)), userInfo: nil, repeats: false)
        
    }
    
    @IBAction func entrarTouchUpInside() {
        longPressTimer?.invalidate()
    }
    
    
    
    @IBAction func salir() {
        ruta = "0"
        longPressTimer?.invalidate()
           
           // Reiniciar el contador de tiempo
           secondsElapsed = 0
        
        longPressTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(handleLongPress(_:)), userInfo: nil, repeats: false)
    }
    
    
    @IBAction func salirTouchUpInside() {
        longPressTimer?.invalidate()

    }
    
    
   
    @objc func handleLongPress(_ timer: Timer) {
        print("Botón mantenido presionado por un segundo")
        longPressTimer?.invalidate()
        vibrateDevice()
        enterAPI()
    }
    func vibrateDevice() {
        guard let generator = impactFeedbackGenerator else {
                return
            }
            
            generator.prepare()
            
            generator.impactOccurred()
    }
    
    func enterAPI(){
        let urlbase = user.string(forKey: "URL");
        let url = URL(string: urlbase! + "arduino/acceso/"+ruta)!
        let token = "Bearer \(String(describing: user.string(forKey: "TOKEN")!))"
        print(token)
        var solicitud = URLRequest(url: url)
        solicitud.httpMethod = "POST"
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
                    
                    
                }
                else if let result = json["message"] as? String {
                    if result == "Unauthenticated." {
                        
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


