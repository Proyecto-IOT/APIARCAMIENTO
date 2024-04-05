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
    var secondsElapsed: Int = 0
    var impactFeedbackGenerator: UIImpactFeedbackGenerator?


    override func viewDidLoad() {
        super.viewDidLoad()
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
        longPressTimer?.invalidate()
           
           // Reiniciar el contador de tiempo
           secondsElapsed = 0
        
        longPressTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(handleLongPress(_:)), userInfo: nil, repeats: false)

    }
    
    @IBAction func entrarTouchUpInside() {
        longPressTimer?.invalidate()
    }
    
    
    
    @IBAction func salir() {
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
    }
    func vibrateDevice() {
        guard let generator = impactFeedbackGenerator else {
                return
            }
            
            generator.prepare()
            
            generator.impactOccurred()
        }
}


