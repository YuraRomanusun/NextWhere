//
//  TravelGeneralVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 11/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
 import MapKit

class TravelGeneralVC: UIViewController,UITextFieldDelegate {

    @IBOutlet var view1:UIView!
    @IBOutlet var view2:UIView!
   @IBOutlet var imgProfile:UIImageView!
    
    @IBOutlet var img1:UIImageView!
    @IBOutlet var img2:UIImageView!
    @IBOutlet var img3:UIImageView!
    
    @IBOutlet var food1:UIImageView!
    @IBOutlet var food2:UIImageView!
    @IBOutlet var food3:UIImageView!
    
    @IBOutlet var btnSave:UIButton!

    @IBOutlet var mapview:MKMapView!

    @IBOutlet var lblname:UILabel!
    @IBOutlet var lblTravel:UILabel!

    var globalMethod = GlobalMethods()

    override func viewDidLoad() {
        super.viewDidLoad()

        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        if Decodedata != nil
        {
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            print(dicData)
            
            lblname.text = dicData.value(forKey: "nombre") as? String
            lblTravel.text = NSString(format:"Travelling to %@",dicData.value(forKey: "hasta_asignado") as! String) as String
        }
        globalMethod.addViewShadw(view: view1)
        globalMethod.addViewShadw(view: view2)

        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.font: UIFont(name: "OpenSans", size: 15.0)!]
        
        imgProfile.layer.cornerRadius = imgProfile.frame.size.height / 2;

        self.navigationController?.navigationBar.tintColor = UIColor.black
        self.navigationItem.title = "Travel Journal"
        
        //  let button1 = UIBarButtonItem(image: UIImage(named: "imagename"), style: .plain, target: self, action: #selector(getter: UIDynamicBehavior.action)) // action:#selector(Class.MethodName) for swift 3
        // self.navigationItem.rightBarButtonItem  = button1
        
        let backButton = UIBarButtonItem(image:  UIImage(named: "media"), style: .plain, target: self, action: #selector(self.clickonBack))
        
        self.navigationItem.rightBarButtonItem  = backButton
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @objc func clickonBack()
    {
        
    }
    @IBAction func clickOnClose(sender:UIButton)
    {
        if sender.tag == 101
        {
            img3.isHidden = true
            img2.isHidden = true
            img1.isHidden = true
            
            food1.isHidden = true
            food2.isHidden = true
            food3.isHidden = true
            
            var framr = btnSave.frame
            framr.origin.y = view2.frame.origin.y + view2.frame.size.height + 25
            btnSave.frame = framr
        }
        else if sender.tag == 102
        {
            img3.isHidden = false
            img2.isHidden = true
            img1.isHidden = true
            
            food1.isHidden = false
            food2.isHidden = true
            food3.isHidden = true
        }
        else if sender.tag == 103
        {
            img3.isHidden = true
            img2.isHidden = false
            img1.isHidden = true
            
            food1.isHidden = false
            food2.isHidden = false
            food3.isHidden = true
        }

    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}
