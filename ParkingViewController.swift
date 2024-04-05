//
//  ParkingViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 13/03/24.
//

import UIKit

class ParkingViewController: UIViewController {

    @IBOutlet weak var svParking: UIScrollView!
    @IBOutlet var btnsParkingSpot: [UIButton]!
    @IBOutlet weak var vwSeccion2: UIView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()


    }
    override func viewDidAppear(_ animated: Bool) {
        svParking.contentSize = CGSize(width: 0, height: vwSeccion2.frame.origin.y + vwSeccion2.frame.height + 10)
    }
    

    @IBAction func back() {
        dismiss(animated:true)
    }
    
    
    @IBAction func refresh() {
    }
    
    
    @IBAction func park(_ sender: UIButton) {
    }
    
}
