//
//  TripVideoVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 14/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import WebKit

@available(iOS 11.0, *)
class TripVideoVC: UIViewController,YTPlayerViewDelegate {

    @IBOutlet var loadingSign: UIActivityIndicatorView!
     var webview:WKWebView!
    
    @IBOutlet var playerView:YTPlayerView!
    
    
    let del = UIApplication.shared.delegate as! AppDelegate
    override func viewDidLoad() {
        super.viewDidLoad()

        UserDefaults.standard.set("1", forKey: "playtrip")
        UserDefaults.standard.synchronize()
        
        playerView.autoresizingMask = ([.flexibleHeight, .flexibleWidth])
        playerView.translatesAutoresizingMaskIntoConstraints = false
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.isNavigationBarHidden = true
        
       loadingSign.hidesWhenStopped = true
        loadingSign.startAnimating()

        
        
        let dic = ["controls":0,
                   "playsinline":0,
                   "autohide":0,
                   "showinfo":0,
                   "modestbranding":0]
        
        playerView.delegate = self
        
        playerView.sizeToFit()
        playerView.load(withVideoId: del.VideoUrl as String, playerVars: dic)
        
        playerView.playVideo()

    }
    
    override var shouldAutorotate: Bool {
        return true
    }
    
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return .landscape
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func playerViewDidBecomeReady(_ playerView: YTPlayerView) {
        
        loadingSign.stopAnimating()
        playerView.playVideo()
        
    }
    func playerView(_ playerView: YTPlayerView, didChangeTo state: YTPlayerState) {
        
        switch state {
        case .playing:
            loadingSign.stopAnimating()
            break
        case .paused:
            loadingSign.stopAnimating()
            break
        case .ended:
            self.navigationController?.popViewController(animated: true)
        default:
            //print("player ready state....")
            break
        }
    }
    
    //MARK:- IBACTION METHOD
    
    @IBAction func btnSkipAction(sender:UIButton)
    {
        self.navigationController?.popViewController(animated: true)

    }
   
}
