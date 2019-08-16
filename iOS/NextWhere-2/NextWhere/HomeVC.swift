//
//  HomeVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 10/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import ZDCChat

@available(iOS 11.0, *)
class HomeVC: UIViewController,UIGestureRecognizerDelegate {
    
    @IBOutlet var view1:UIView!
    @IBOutlet var view2:UIView!
    
    @IBOutlet var viewtrip:UIView!
    
    @IBOutlet var lblname1:UILabel!
    @IBOutlet var lblname2:UILabel!
    @IBOutlet var lbltype1:UILabel!
    @IBOutlet var lbltype2:UILabel!
    
    @IBOutlet var img1:UIImageView!
    @IBOutlet var img2:UIImageView!
    
    @IBOutlet var mainScroll:UIScrollView!
    @IBOutlet var lblLocation:UILabel!
    
    @IBOutlet var imgMain:UIImageView!
    var globalMethod = GlobalMethods()
    
    let del = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let tripvideo = UserDefaults.standard.value(forKey: "playtrip") as? NSString
        if tripvideo == nil
        {
            let tripview = self.storyboard?.instantiateViewController(withIdentifier: "TripVideoVC") as! TripVideoVC
            self.navigationController?.pushViewController(tripview, animated: true)
        }
        
        let imgView = UIView(frame: CGRect(x: 0, y: 0, width: 120, height: 50))
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 120, height: 50))
        imageView.contentMode = .scaleAspectFit
        let image = UIImage(named: "img_logo")
        imageView.image = image
        imgView.addSubview(imageView)
        navigationItem.titleView = imgView
        self.navigationItem.title = ""
        
        img1.layer.cornerRadius = img1.frame.size.height / 2;
        img2.layer.cornerRadius = img2.frame.size.height / 2;
        
        
        globalMethod.addViewShadw(view: view1)
        globalMethod.addViewShadw(view: view2)
        
        mainScroll.contentSize = CGSize(width: self.view.frame.size.width, height: 410)
        
        let gesture = UITapGestureRecognizer(target: self, action: #selector(clickOnTrip(tap:)))
        gesture.delegate = self
        viewtrip.addGestureRecognizer(gesture)
    }
    override func viewWillAppear(_ animated: Bool) {
        
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.isNavigationBarHidden = false
        self.navigationItem.hidesBackButton = true

        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        if Decodedata != nil
        {
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            
            if dicData.value(forKey: "vista_inmediata") as! String == "0" {
//                let waitMessageView = self.storyboard?.instantiateViewController(withIdentifier: "WaitMessageVC") as! WaitMessageVC
//                self.navigationController?.pushViewController(waitMessageView, animated: true)
//                self.dismiss(animated: true, completion: nil)
            }
            
            //print(dicData)
            let arr = dicData.value(forKey: "pasajeros") as! NSArray
            if arr.count > 0
            {
                let dic1 = arr.object(at: 0) as? NSDictionary
                let dic2 = arr.object(at: 1) as? NSDictionary
                
                if dic1 != nil
                {
                    lblname1.text = NSString(format:"%@ %@",dic1?.value(forKey: "nombre") as! String,dic1?.value(forKey: "apellido") as! String) as String
                    lbltype1.text = dic1?.value(forKey: "nacionalidad")  as? String
                }
                
                if dic2 != nil
                {
                    lblname2.text = NSString(format:"%@ %@",dic2?.value(forKey: "nombre") as! String,dic2?.value(forKey: "apellido") as! String) as String
                    
                    lbltype2.text = dic2?.value(forKey: "nacionalidad")  as? String
                }
                let location = dicData.value(forKey: "hasta_asignado") as? String
                
                lblLocation.text = location?.uppercased()
                
            }
            
            
            let mainImage = dicData.value(forKey: "ruta_imagen_destino") as? String
            if mainImage != nil
            {
                let imgStr = NSString(format:"%@%@",FileDomain,mainImage!) as String
                let imgUrl = URL(string: imgStr)
                imgMain.sd_setImage(with: imgUrl, placeholderImage: UIImage(named:"img_background_my_trip_details"))
                
            }
            
            del.user1Date = dicData.value(forKey: "fecha_ida") as! NSString
            del.user2Date = dicData.value(forKey: "fecha_hasta") as! NSString
        }
        
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @objc func clickOnTrip(tap:UITapGestureRecognizer)
    {
        let tripview = self.storyboard?.instantiateViewController(withIdentifier: "TripVideoVC") as! TripVideoVC
        self.navigationController?.pushViewController(tripview, animated: true)
    }
    
    
    //MARK:- IBACTION METHOD
    
    @IBAction func btnHomeAction(sender:UIButton)
    {
        if sender.tag == 1
        {
            let detailview = self.storyboard?.instantiateViewController(withIdentifier: "TripDetailVC") as! TripDetailVC
            self.navigationController?.pushViewController(detailview, animated: true)
        }
        else if sender.tag == 2
        {
            let supView = self.storyboard?.instantiateViewController(withIdentifier: "SurpriseWebVC") as! SurpriseWebVC
            self.navigationItem.title = ""
            self.navigationController?.pushViewController(supView, animated: true)
            
        }
        else if sender.tag == 3
        {
            let guideview = self.storyboard?.instantiateViewController(withIdentifier: "CityGuideVC") as! CityGuideVC
            self.navigationItem.title = ""
            self.navigationController?.pushViewController(guideview, animated: true)
        }
        else if sender.tag == 4
        {
            let travelview = self.storyboard?.instantiateViewController(withIdentifier: "TravelGeneralVC") as! TravelGeneralVC
            self.navigationItem.title = ""
            self.navigationController?.pushViewController(travelview, animated: true)
        }
        else if sender.tag == 5
        {
            ZDCChat.instance().api.trackEvent("Chat button pressed: (all fields required)")
            
            ZDCChat.start(in: self.navigationController) { (config) in
                
                self.navigationController?.navigationBar.isTranslucent = false
                
                config?.preChatDataRequirements.name = ZDCPreChatDataRequirement.required
                config?.preChatDataRequirements.email = ZDCPreChatDataRequirement.required
                config?.preChatDataRequirements.phone = ZDCPreChatDataRequirement.required
                config?.preChatDataRequirements.department = ZDCPreChatDataRequirement.required
                config?.preChatDataRequirements.message = ZDCPreChatDataRequirement.required
                
                
            }
            
        }
    }
    @IBAction func btnLogoutAction(sender:UIButton)
    {
        UserDefaults.standard.removeObject(forKey: "Userdata")
        UserDefaults.standard.synchronize()
        del.setRoot()
    }
    
    
}
