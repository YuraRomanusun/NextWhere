//
//  playerVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 13/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import AVFoundation

@available(iOS 11.0, *)
class playerVC: UIViewController {

    var moviePlayer: AVPlayer?
    var timevalue = TimeInterval()

     var timer = Timer()
    
    let del = UIApplication.shared.delegate as! AppDelegate
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationController?.isNavigationBarHidden = true
        self.navigationController?.navigationBar.isTranslucent = false
        timevalue = 0
        let path = Bundle.main.path(forResource: "USA map white BG", ofType:"mp4")
        let url = NSURL.fileURL(withPath: path!)
        moviePlayer = AVPlayer(url: url)
        let playerLayer = AVPlayerLayer(player: moviePlayer)
        playerLayer.frame = self.view.bounds
        self.view.layer.addSublayer(playerLayer)
        moviePlayer?.play()
        
         timer =  Timer.scheduledTimer(timeInterval: 1, target: self, selector:#selector(setTimer), userInfo: nil, repeats: true)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func viewWillDisappear(_ animated: Bool) {
    
       moviePlayer?.pause()
        timer.invalidate()
        
    }
    @objc func setTimer()
    {
        timevalue = timevalue + 1;
       if timevalue == 7
        {
            
            let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        
            if Decodedata != nil
            {
                
                let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
                let mostrarDestino = dicData.value(forKey: "mostrar_destino") as? NSInteger
                let vistaInmediata = dicData.value(forKey: "vista_inmediata") as? String
                
                if vistaInmediata == "0" && mostrarDestino == 0 {
                    let waitMessageView = self.storyboard?.instantiateViewController(withIdentifier: "WaitMessageVC") as! WaitMessageVC
                    self.navigationController?.pushViewController(waitMessageView, animated: true)
                    self.dismiss(animated: true, completion: nil)
                    return
                }
                else {
                    let homeview = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController?.pushViewController(homeview, animated: true)
                }

                
            }
            else
            {
                
                let mainview = self.storyboard?.instantiateViewController(withIdentifier: "ViewController") as! ViewController
                self.navigationController?.pushViewController(mainview, animated: true)
            }
           
        }
        
        
    }
    



}
