//
//  ViewController.swift
//  APIARCAMIENTO
//
//  Created by Mac24 on 08/03/24.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var imvSplash: UIImageView!
    let defaults = UserDefaults.standard
    
    override func viewDidLoad() {
            super.viewDidLoad()
            
        //defaults.set("http://192.168.80.130:8000/api/v3/", forKey: "URL")
        defaults.set("http://192.168.254.15:8000/api/v3/", forKey: "URL")

            let w  = 0.80 * view.frame.width
            let h = 0.43 * w
            let x = (view.frame.width - w) / 2
            let y = -h
            imvSplash.frame = CGRect(x: x, y: y, width: w, height: h)
            imvSplash.alpha = 0
        }
        
    override func viewDidAppear(_ animated: Bool) {
//        self.defaults.set(nil, forKey: "TOKEN") 

        UIView.animate(withDuration: 3) {
                    self.imvSplash.frame.origin.y = (self.view.frame.height - self.imvSplash.frame.height) / 2
                    self.imvSplash.alpha = 1
                }completion: { res in
                    if self.defaults.string(forKey: "TOKEN") != nil{
                        self.performSegue(withIdentifier: "sgIndexToken", sender: nil)
                    }else{
                        self.performSegue(withIdentifier: "sgSplash", sender: nil)
                    }
                    
                }
        }

        @IBAction func regresar() {
            dismiss(animated: true)
        }
        

    }
