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
    
    override func viewDidLoad() {
        super.viewDidLoad()
//hola
        vIncidentes.layer.cornerRadius = 10
        vSalida.layer.cornerRadius = 10
        vEntrada.layer.cornerRadius = 10
        btnEstacionamiento.layer.cornerRadius = 10


        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        svHome.contentSize = CGSize(width: 0, height: btnEstacionamiento.frame.origin.y + btnEstacionamiento.frame.height + 10)
    }
   

    @IBAction func entrar() {
    }
    
    @IBAction func salir() {
    }
}
