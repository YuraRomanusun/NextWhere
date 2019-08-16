
//
//  SupriseVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 11/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import Alamofire
import CoreLocation
import FBSDKShareKit
import FBSDKCoreKit

class SupriseVC: UIViewController,UIImagePickerControllerDelegate,UINavigationControllerDelegate,UITextFieldDelegate,CLLocationManagerDelegate,FBSDKSharingDelegate,UIDocumentInteractionControllerDelegate{
    
    
    
    var documentController: UIDocumentInteractionController!
    
    @IBOutlet var btnunlock:UIButton!
    
    @IBOutlet var btnDone:UIButton!
    @IBOutlet var viewcamera:UIView!
    @IBOutlet var imgPoster:UIImageView!
    @IBOutlet var imgChecked:UIImageView!
    @IBOutlet var lblDesc:UILabel!
    @IBOutlet var Indicator:UIActivityIndicatorView!
    
    
    @IBOutlet var viewUpload:UIView!
    @IBOutlet var viewQuestion:UIView!
    @IBOutlet var imgLock:UIImageView!
    
    @IBOutlet var view1:UIView!
    @IBOutlet var view2:UIView!
    @IBOutlet var view3:UIView!
    @IBOutlet var view4:UIView!
    @IBOutlet var view5:UIView!
    
    @IBOutlet var lbl1:UILabel!
    @IBOutlet var lbl2:UILabel!
    @IBOutlet var lbl3:UILabel!
    @IBOutlet var lbl4:UILabel!
    @IBOutlet var lbl5:UILabel!
    
    
    @IBOutlet var img1:UIImageView!
    @IBOutlet var img2:UIImageView!
    @IBOutlet var img3:UIImageView!
    @IBOutlet var img4:UIImageView!
    @IBOutlet var img5:UIImageView!
    
    @IBOutlet var lblline1:UILabel!
    @IBOutlet var lblline2:UILabel!
    @IBOutlet var lblline3:UILabel!
    @IBOutlet var lblline4:UILabel!
    @IBOutlet var lblline5:UILabel!
    
    
    @IBOutlet var lbldot3:UILabel!
    @IBOutlet var lbldot4:UILabel!
    
    @IBOutlet var imgtask1:UIImageView!
    @IBOutlet var imgtask2:UIImageView!
    @IBOutlet var imgtask3:UIImageView!
    @IBOutlet var imgtask4:UIImageView!
    @IBOutlet var imgtask5:UIImageView!
    
    var userCode = NSString()
    var arrImgData = NSArray()
    
    var strTag = NSInteger()
    let SelectColor = UIColor(red: 31.0/2550, green: 128.0/255.0, blue: 146.0/255.0, alpha: 1.0)
    let UnSelectColor = UIColor.lightGray
    
    var isUpload = Bool()
    
    var globalMethod = GlobalMethods()
    
    var TempImage = UIImageView()
    
    @IBOutlet var txtAnswer:UITextField!
    
    @IBOutlet var lblQusetion:UILabel!
    @IBOutlet var btnSub:UIButton!
    @IBOutlet var btnCheckIn:UIButton!
    
    @IBOutlet var imgQuestion:UIImageView!
    @IBOutlet var imgCheckIn:UIImageView!
    
    
    
    @IBOutlet var viewsubQue:UIView!
    @IBOutlet var viewsubUpl:UIView!
    
    @IBOutlet var viewUploadDone:UIView!
    
    @IBOutlet var lblCheckIn:UILabel!
    @IBOutlet var viewCheckIn:UIView!
    @IBOutlet var viewSubCheckIn:UIView!
    @IBOutlet var imgCheck:UIImageView!
    
    
    
    let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        Indicator.hidesWhenStopped = true
        
        viewUpload.isHidden = true
        viewQuestion.isHidden = true
        imgChecked.isHidden  = true
        viewcamera.isHidden = false
        btnDone.isHidden = true
        viewUploadDone.isHidden = true
        viewCheckIn.isHidden = true
        
        viewsubQue.layer.cornerRadius = 10
        viewsubUpl.layer.cornerRadius = 10
        viewSubCheckIn.layer.cornerRadius = 10
        btnCheckIn.layer.cornerRadius = 5
        isUpload = false
        //lblDesc.text  = "Take a photo at a Coffee Shop"
        view3.layer.borderColor = UnSelectColor.cgColor
        view3.layer.borderWidth = 1
        
        view4.layer.borderColor = UnSelectColor.cgColor
        view4.layer.borderWidth = 1
        
        view5.layer.borderColor = UnSelectColor.cgColor
        view5.layer.borderWidth = 1
        if UIScreen.main.bounds.size.height > 568
        {
            var frame = imgLock.frame
            frame.origin.x = imgLock.frame.origin.x - 30
            imgLock.frame = frame
        }
        
        
        setUnselected(view: view1, img: img1)
        setUnselected(view: view2, img: img2)
        setUnselected(view: view3, img: img3)
        setUnselected(view: view4, img: img4)
        setUnselected(view: view5, img: img5)
        
        lblline1.isHidden = true
        lblline2.isHidden = true
        lblline3.isHidden = true
        lblline4.isHidden = true
        lblline5.isHidden = true
        
        let dic1 = UserDefaults.standard.object(forKey: "dic1") as? NSString
        if dic1 == "1"
        {
            lblline1.isHidden = false
            setCompleted(view: view1, img: img1)
            setSelected(view: view2, img: img2)
            view1.isUserInteractionEnabled = false
        }
        //            else
        //            {
        //                setUnselected(view: view1, img: img1)
        //                lblline1.isHidden = true
        //
        //            }
        
        
        let dic2 = UserDefaults.standard.object(forKey: "dic2") as? NSString
        if dic2 == "1"
        {
            lblline2.isHidden = false
            setCompleted(view: view2, img: img2)
            
            setSelected(view: view3, img: img3)
            view2.isUserInteractionEnabled = false
            
            
        }
        
        
        
        let dic3 = UserDefaults.standard.object(forKey: "dic3") as? NSString
        
        if dic3 == "1"
        {
            lblline3.isHidden = false
            setCompleted(view: view3, img: img3)
            
            setSelected(view: view4, img: img4)
            view3.isUserInteractionEnabled = false
            
            
        }
        
        
        let dic4 = UserDefaults.standard.object(forKey: "dic4") as? NSString
        
        if dic4 == "1"
        {
            lblline4.isHidden = false
            setCompleted(view: view4, img: img4)
            
            setSelected(view: view5, img: img5)
            view4.isUserInteractionEnabled = false
            
            
        }
        
        
        
        let dic5 = UserDefaults.standard.object(forKey: "dic5") as? NSString
        
        if dic5 == "1"
        {
            lblline5.isHidden = false
            setCompleted(view: view5, img: img5)
            view5.isUserInteractionEnabled = false
            
            
        }
        
        
        var frame = imgCheck.frame
        frame.origin.x = imgCheckIn.frame.origin.x + 2
        imgCheck.frame = frame
        
        frame = btnCheckIn.frame
        frame.origin.x = imgCheck.frame.origin.x + imgCheck.frame.size.width + 8
        btnCheckIn.frame = frame
        
        
        getData()
        
        
        
        
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func viewWillAppear(_ animated: Bool) {
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.font: UIFont(name: "OpenSans", size: 15.0)!]
        
        
        self.navigationController?.navigationBar.tintColor = UIColor.black
        self.navigationItem.title = "View Unlock Surprise"
    }
    
    //MARK:- IBACTION METHOD
    @IBAction func btnUnlockSurpriseAction(sender:UIButton)
    {
        if self.userCode == "S"
        {
            if img1.image == UIImage(named:"Pin_select") && img2.image == UIImage(named:"Pin_select") && img3.image == UIImage(named:"Pin_select")
            {
                let surview = self.storyboard?.instantiateViewController(withIdentifier: "SurpriseVC") as! SurpriseVC
                surview.strTag = "3"
                self.navigationItem.title = ""
                self.navigationController?.pushViewController(surview, animated: true)
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Unlock atleat 3 surprise!", globalAlert: self)
            }
        }
        else
        {
            if img1.image == UIImage(named:"Pin_select") && img2.image == UIImage(named:"Pin_select") && img3.image == UIImage(named:"Pin_select") && img4.image == UIImage(named:"Pin_select")
                && img5.image == UIImage(named:"Pin_select")
            {
                let surview = self.storyboard?.instantiateViewController(withIdentifier: "SurpriseVC") as! SurpriseVC
                surview.strTag = "5"
                self.navigationItem.title = ""
                self.navigationController?.pushViewController(surview, animated: true)
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Unlock atleat 5 surprise!", globalAlert: self)
            }
            
        }
        
        
    }
    
    @IBAction func btnCloseAction(sender:UIButton)
    {
        viewUpload.isHidden = true
        imgChecked.isHidden  = true
        viewcamera.isHidden = false
        btnDone.isHidden = true
        viewUploadDone.isHidden = true
        
        // lblDesc.text  = "Take a photo at a Coffee Shop"
    }
    @IBAction func btnUploadAction()
    {
        
        if TempImage.image != nil
        {
            let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            let userName = dicData.value(forKey: "nombre") as? String
            
            lblDesc.text = NSString(format:"Congratulations %@, Task Completed!",userName!) as String
            
            imgChecked.isHidden = false
            viewcamera.isHidden = true
            btnDone.isHidden = false
            viewUploadDone.isHidden = false
            
            isUpload = false
            
            if strTag == 1
            {
                lblline1.isHidden = false
                view1.isUserInteractionEnabled = false
                
            }
            else if strTag == 2
            {
                lblline2.isHidden = false
                view2.isUserInteractionEnabled = false
                
            }
            else if strTag == 3
            {
                lblline3.isHidden = false
                view3.isUserInteractionEnabled = false
                
                
            }
            else if strTag == 4
            {
                lblline4.isHidden = false
                view4.isUserInteractionEnabled = false
            }
            else if strTag == 5
            {
                lblline5.isHidden = false
                view5.isUserInteractionEnabled = false
            }
            
            
        }
        else
        {
            globalMethod.displayAlert(strMessge: "Upload Photo", globalAlert: self)
            
        }
        
        
        
    }
    @IBAction func btnDoneAction()
    {
        isUpload = false
        viewUpload.isHidden = true
        imgChecked.isHidden  = true
        viewcamera.isHidden = false
        btnDone.isHidden = true
        viewUploadDone.isHidden = true
        isUpload = false
        imgPoster.image = nil
        TempImage.image = nil
        lblDesc.text = ""
        
        if strTag == 1
        {
            setCompleted(view: view1, img: img1)
            setSelected(view: view2, img: img2)
            
            UserDefaults.standard.setValue("1", forKey: "dic1")
            UserDefaults.standard.synchronize()
        }
        else if strTag == 2
        {
            setCompleted(view: view2, img: img2)
            setSelected(view: view3, img: img3)
            
            UserDefaults.standard.setValue("1", forKey: "dic2")
            UserDefaults.standard.synchronize()
        }
        else if strTag == 3
        {
            setCompleted(view: view3, img: img3)
            setSelected(view: view4, img: img4)
            
            UserDefaults.standard.setValue("1", forKey: "dic3")
            UserDefaults.standard.synchronize()
            
        }
        else if strTag == 4
        {
            setCompleted(view: view4, img: img4)
            setSelected(view: view5, img: img5)
            
            UserDefaults.standard.setValue("1", forKey: "dic4")
            UserDefaults.standard.synchronize()
        }
        else if strTag == 5
        {
            setCompleted(view: view5, img: img5)
            
            UserDefaults.standard.setValue("1", forKey: "dic5")
            UserDefaults.standard.synchronize()
        }
        
        
        //lblDesc.text  = "Take a photo at a Coffee Shop"
    }
    @IBAction func btnCameraAction()
    {
        if UIImagePickerController.isSourceTypeAvailable(.camera)
        {
            let picker = UIImagePickerController()
            picker.delegate = self
            picker.sourceType =  .camera
            picker.allowsEditing = false
            isUpload = true
            self.present(picker, animated: true, completion: nil)
        }
        else
        {
            
        }
    }
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        imgPoster.image = image
        imgPoster.contentMode = .scaleAspectFill
        lblDesc.text = ""
        TempImage.image = image
        dismiss(animated:true, completion: nil)
        
    }
    @IBAction func btnAction(sender:UIButton)
    {
        strTag = sender.tag
        let dic = arrImgData.object(at: sender.tag - 1) as! NSDictionary
        let catType = dic.value(forKey: "categoria_prenda") as? String
        if catType == "Photograph" || catType == "photograph"
        {
            if strTag == 1
            {
                setSelected(view: view1, img: img1)
                imgPoster.contentMode = .scaleAspectFit
                viewUpload.isHidden = false
            }
            else if strTag == 2
            {
                let dic1 = UserDefaults.standard.object(forKey: "dic1") as? NSString
                if dic1 != nil
                {
                    
                    self.lblDesc.text = dic.value(forKey: "descripcion_prenda") as? String
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgPoster.sd_setImage(with: imgUrl)
                    
                    setSelected(view: view2, img: img2)
                    imgPoster.contentMode = .scaleAspectFit
                    viewUpload.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                }
                
            }
            else if strTag == 3
            {
                let dic2 = UserDefaults.standard.object(forKey: "dic2") as? NSString
                if dic2 != nil
                {
                    self.lblDesc.text = dic.value(forKey: "descripcion_prenda") as? String
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgPoster.sd_setImage(with: imgUrl)
                    
                    setSelected(view: view3, img: img3)
                    imgPoster.contentMode = .scaleAspectFit
                    viewUpload.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 4
            {
                let dic3 = UserDefaults.standard.object(forKey: "dic3") as? NSString
                if dic3 != nil
                {
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgPoster.sd_setImage(with: imgUrl)
                    
                    setSelected(view: view4, img: img4)
                    imgPoster.contentMode = .scaleAspectFit
                    viewUpload.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 5
            {
                let dic4 = UserDefaults.standard.object(forKey: "dic4") as? NSString
                if dic4 != nil
                {
                    self.lblDesc.text = dic.value(forKey: "descripcion_prenda") as? String
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgPoster.sd_setImage(with: imgUrl)
                    
                    setSelected(view: view5, img: img5)
                    imgPoster.contentMode = .scaleAspectFit
                    viewUpload.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            
            self.lblDesc.text = dic.value(forKey: "descripcion_prenda") as? String
            
            
        }
        else if catType == "question"
        {
            if strTag == 1
            {
                setSelected(view: view1, img: img1)
                viewQuestion.isHidden = false
                
                self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                
                var imgStr = dic.value(forKey: "imagen_prenda") as? String
                imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                
                let imgUrl = URL(string: imgStr as! String)
                
                self.imgQuestion.sd_setImage(with: imgUrl)
                
            }
            else if strTag == 2
            {
                let dic1 = UserDefaults.standard.object(forKey: "dic1") as? NSString
                if dic1 != nil
                {
                    setSelected(view: view2, img: img2)
                    viewQuestion.isHidden = false
                    
                    self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                    
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgQuestion.sd_setImage(with: imgUrl)
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
                
                
            }
            else if strTag == 3
            {
                let dic2 = UserDefaults.standard.object(forKey: "dic2") as? NSString
                if dic2 != nil
                {
                    setSelected(view: view3, img: img3)
                    viewQuestion.isHidden = false
                    
                    self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                    
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgQuestion.sd_setImage(with: imgUrl)
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
                
            }
            else if strTag == 4
            {
                let dic3 = UserDefaults.standard.object(forKey: "dic3") as? NSString
                if dic3 != nil
                {
                    setSelected(view: view4, img: img4)
                    viewQuestion.isHidden = false
                    
                    self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                    
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgQuestion.sd_setImage(with: imgUrl)
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 5
            {
                let dic4 = UserDefaults.standard.object(forKey: "dic4") as? NSString
                if dic4 != nil
                {
                    setSelected(view: view5, img: img5)
                    viewQuestion.isHidden = false
                    
                    self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                    
                    var imgStr = dic.value(forKey: "imagen_prenda") as? String
                    imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    
                    let imgUrl = URL(string: imgStr as! String)
                    
                    self.imgQuestion.sd_setImage(with: imgUrl)
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            
            lblQusetion.text = dic.value(forKey: "nombre_prenda") as? String
        }
        else if catType == "check-in"
        {
            
            
            
            if strTag == 1
            {
                setSelected(view: view1, img: img1)
                viewCheckIn.isHidden = false
                
            }
            else if strTag == 2
            {
                let dic1 = UserDefaults.standard.object(forKey: "dic1") as? NSString
                if dic1 != nil
                {
                    setSelected(view: view2, img: img2)
                    viewCheckIn.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 3
            {
                let dic2 = UserDefaults.standard.object(forKey: "dic2") as? NSString
                if dic2 != nil
                {
                    setSelected(view: view3, img: img3)
                    viewCheckIn.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 4
            {
                let dic3 = UserDefaults.standard.object(forKey: "dic3") as? NSString
                if dic3 != nil
                {
                    setSelected(view: view4, img: img4)
                    viewCheckIn.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            else if strTag == 5
            {
                let dic4 = UserDefaults.standard.object(forKey: "dic4") as? NSString
                if dic4 != nil
                {
                    setSelected(view: view5, img: img5)
                    viewCheckIn.isHidden = false
                }
                else
                {
                    globalMethod.displayAlert(strMessge: "unlock previous task", globalAlert: self)
                    
                }
            }
            
            lblCheckIn.text = dic.value(forKey: "nombre_prenda") as? String
            
            var imgStr = dic.value(forKey: "imagen_prenda") as? String
            imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
            
            let imgUrl = URL(string: imgStr as! String)
            
            self.imgCheckIn.sd_setImage(with: imgUrl)
            
            
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        locationManager.stopUpdatingLocation()
        
        let locValue:CLLocationCoordinate2D = manager.location!.coordinate
        // print(locValue.latitude)
        // print(locValue.longitude)
        
        let str1 = NSString(format:"%.6f",locValue.latitude) as? NSString
        let str2 = NSString(format:"%.6f",locValue.longitude) as? NSString
        
        
        //print(str1)
        // print(str2)
        
        let dic = arrImgData.object(at: strTag - 1) as! NSDictionary
        let loc1 = dic.value(forKey: "latitud") as? NSString
        let loc2 = dic.value(forKey: "longitud") as? NSString
        
        //loc1 = "21.2124256"
        // loc2 = "72.8849013"
        let userLocation = CLLocation(latitude: locValue.latitude, longitude: locValue.longitude)
        let surLocation = CLLocation(latitude: (loc1?.doubleValue)!, longitude: (loc2?.doubleValue)!)
        
        // print(userLocation)
        // print(surLocation)
        
        let locmeter = userLocation.distance(from: surLocation)
        print(locmeter)
        
        //   let strLat =  NSString(format:"%.3f",(loc1?.floatValue)!) as? NSString
        //  let strLon = NSString(format:"%.3f",(loc2?.floatValue)!) as? NSString
        
        
        if  locmeter <= 500
        {
            if strTag == 1
            {
                lblline1.isHidden = false
                setCompleted(view: view1, img: img1)
                setSelected(view: view2, img: img2)
                
                UserDefaults.standard.setValue("1", forKey: "dic1")
                UserDefaults.standard.synchronize()
            }
            else if strTag == 2
            {
                lblline2.isHidden = false
                
                setCompleted(view: view2, img: img2)
                setSelected(view: view3, img: img3)
                
                UserDefaults.standard.setValue("1", forKey: "dic2")
                UserDefaults.standard.synchronize()
            }
            else if strTag == 3
            {
                
                lblline3.isHidden = false
                
                setCompleted(view: view3, img: img3)
                setSelected(view: view4, img: img4)
                
                UserDefaults.standard.setValue("1", forKey: "dic3")
                UserDefaults.standard.synchronize()
            }
            else if strTag == 4
            {
                lblline4.isHidden = false
                
                setCompleted(view: view4, img: img4)
                setSelected(view: view5, img: img5)
                
                UserDefaults.standard.setValue("1", forKey: "dic4")
                UserDefaults.standard.synchronize()
            }
            else if strTag == 5
            {
                
                lblline5.isHidden = false
                
                setCompleted(view: view5, img: img5)
                
                UserDefaults.standard.setValue("1", forKey: "dic5")
                UserDefaults.standard.synchronize()
            }
            
            let alertController = UIAlertController(title: "YOU SEEM TO BE AT THE CORRECT PLACE!", message: "TASK COMPLETED", preferredStyle: .alert)
            
            let okAction = UIAlertAction(title: "Ok", style: .default) { (alert) in
                
                self.viewCheckIn.isHidden = true
                
            }
            
            alertController.addAction(okAction)
            self.present(alertController, animated:true, completion: nil)
            
        }
        else
        {
            globalMethod.displayAlert(strMessge: "Incorrect Place,Task Incomplete!", globalAlert: self)
            
        }
        
        Indicator.stopAnimating()
    }
    
    func setSelected(view:UIView,img:UIImageView)
    {
        view.layer.borderWidth = 1;
        view.layer.borderColor = SelectColor.cgColor
        
        img.image = UIImage(named:"Pin_color")
    }
    func setUnselected(view:UIView,img:UIImageView)
    {
        view.layer.borderWidth = 1;
        view.layer.borderColor = UnSelectColor.cgColor
        
        img.image = UIImage(named:"Pin")
    }
    func setCompleted(view:UIView,img:UIImageView)
    {
        view.layer.borderWidth = 1;
        view.layer.borderColor = UnSelectColor.cgColor
        
        img.image = UIImage(named:"Pin_select")
        view.isUserInteractionEnabled = false
    }
    @IBAction func btnAnswerAction(sender:UIButton)
    {
        txtAnswer.resignFirstResponder()
        
        let dic = arrImgData.object(at: strTag - 1) as! NSDictionary
        let strAnswer = dic.value(forKey: "respuesta") as? String
        print(strAnswer?.lowercased())
        print(strAnswer)
        if strTag == 1
        {
            if txtAnswer.text == strAnswer || txtAnswer.text == strAnswer?.lowercased()
            {
                lblline1.isHidden = false
                setCompleted(view: view1, img: img1)
                setSelected(view: view2, img: img2)
                
                UserDefaults.standard.setValue("1", forKey: "dic1")
                UserDefaults.standard.synchronize()
                
                self.showAlert()
                
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Incorrect Answer!", globalAlert: self)
            }
        }
        else if strTag == 2
        {
            if txtAnswer.text == strAnswer
            {
                lblline2.isHidden = false
                
                setCompleted(view: view2, img: img2)
                setSelected(view: view3, img: img3)
                
                UserDefaults.standard.setValue("1", forKey: "dic2")
                UserDefaults.standard.synchronize()
                
                self.showAlert()
                
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Incorrect Answer!", globalAlert: self)
            }
        }
        else if strTag == 3
        {
            if txtAnswer.text == strAnswer
            {
                lblline3.isHidden = false
                
                setCompleted(view: view3, img: img3)
                setSelected(view: view4, img: img4)
                
                UserDefaults.standard.setValue("1", forKey: "dic3")
                UserDefaults.standard.synchronize()
                
                self.showAlert()
                
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Incorrect Answer!", globalAlert: self)
            }
        }
        else if strTag == 4
        {
            if txtAnswer.text == strAnswer
            {
                lblline4.isHidden = false
                setCompleted(view: view4, img: img4)
                setSelected(view: view5, img: img5)
                
                UserDefaults.standard.setValue("1", forKey: "dic4")
                UserDefaults.standard.synchronize()
                
                self.showAlert()
                
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Incorrect Answer!", globalAlert: self)
            }
        }
        else if strTag == 5
        {
            if txtAnswer.text == strAnswer
            {
                lblline5.isHidden = false
                
                setCompleted(view: view5, img: img5)
                
                UserDefaults.standard.setValue("1", forKey: "dic5")
                UserDefaults.standard.synchronize()
                
                self.showAlert()
                
            }
            else
            {
                globalMethod.displayAlert(strMessge: "Incorrect Answer!", globalAlert: self)
            }
        }
        
    }
    
    @IBAction func btnCloseAnswerAction(sender:UIButton)
    {
        viewQuestion.isHidden = true
        txtAnswer.resignFirstResponder()
    }
    @IBAction func btnCloseCheckInAction(sender:UIButton)
    {
        viewCheckIn.isHidden = true
    }
    @IBAction func btnShareAction(sender:UIButton)
    {
        if sender.tag == 1001
        {
            let sharePhoto = FBSDKSharePhoto()
            //sharePhoto.caption =
            sharePhoto.image = imgPoster.image
            
            let content = FBSDKSharePhotoContent()
            // content.hashtag = FBSDKHashtag.init(string: "#surprisegetaways #next-where")
            content.photos = [sharePhoto]
            
            FBSDKShareDialog.show(from: self, with: content, delegate: self)
        }
        else
        {
            
            DispatchQueue.main.async {
                
                let instagramURL = URL(string: "instagram://")
                
                if UIApplication.shared.canOpenURL(instagramURL!)
                {
                    let imagedata = UIImageJPEGRepresentation(self.imgPoster.image!, 100)
                    
                    let writePath = (NSTemporaryDirectory() as NSString).appendingPathComponent("instagram.igo")
                    
                    do {
                        try imagedata?.write(to: URL(fileURLWithPath: writePath), options: .atomic)
                        
                    } catch {
                        
                        print(error)
                    }
                    
                    let fileURL = URL(fileURLWithPath: writePath)
                    
                    self.documentController = UIDocumentInteractionController(url: fileURL)
                    self.documentController.delegate = self
                    self.documentController.uti = "com.instagram.exlusivegram"
                    //self.documentController.annotation = ["InstagramCaption": "surprisegetaways"]
                    
                    
                    // if UIDevice.current.userInterfaceIdiom == .phone {
                    
                    self.documentController.presentOpenInMenu(from: self.view.bounds, in: self.view, animated: true)
                    
                    // }
                    
                }
                else {
                    
                    print(" Instagram is not installed ")
                }
                
                
                
            }
            
            /*   DispatchQueue.main.async {
             
             //Share To Instagrma:
             
             let instagramURL = URL(string: "instagram://app")
             
             if UIApplication.shared.canOpenURL(instagramURL) {
             
             let imageData = UIImageJPEGRepresentation(<YOURIMAGE>!, 100)
             
             let writePath = (NSTemporaryDirectory() as NSString).appendingPathComponent("instagram.igo")
             
             do {
             try imageData?.write(to: URL(fileURLWithPath: writePath), options: .atomic)
             
             } catch {
             
             print(error)
             }
             
             let fileURL = URL(fileURLWithPath: writePath)
             
             self.documentController = UIDocumentInteractionController(url: fileURL)
             
             self.documentController.delegate = self
             
             self.documentController.uti = "com.instagram.exlusivegram"
             
             if UIDevice.current.userInterfaceIdiom == .phone {
             
             self.documentController.presentOpenInMenu(from: self.view.bounds, in: self.view, animated: true)
             
             } else {
             
             self.documentController.presentOpenInMenu(from: self.IGBarButton, animated: true)
             
             }
             
             
             } else {
             
             print(" Instagram is not installed ")
             }
             }
             */
            
            
            
        }
    }
    
    @IBAction func btnCheckInAction(sender:UIButton)
    {
        Indicator.startAnimating()
        locationManager.delegate = self;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    //MARK:- API METHOD
    
    func getData()
    {
        Indicator.startAnimating()
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
        
        let ApiURL = NSString(format:"%@?id=%@",DestiURL,dicData.value(forKey: "id_destino") as! NSString) as String
        
        Alamofire.request(ApiURL).responseJSON { (response) in
            
            self.Indicator.stopAnimating()
            
            if let json = response.result.value {
                print("JSON: \(json)")
                
                self.arrImgData = json as! NSArray
                //  print(self.arrImgData.count)
                
                for var i in 0..<self.arrImgData.count
                {
                    let dic = self.arrImgData.object(at: i) as! NSDictionary
                    if i == 0
                    {
                        self.view1.isHidden = false
                        
                        self.lbl1.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        self.lblDesc.text = dic.value(forKey: "descripcion_prenda") as? String
                        var imgStr = dic.value(forKey: "imagen_prenda") as? String
                        imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                        
                        let imgUrl = URL(string: imgStr as! String)
                        
                        self.imgPoster.sd_setImage(with: imgUrl)
                        
                        let catType = dic.value(forKey: "categoria_prenda") as? String
                        if catType == "Photograph" || catType == "photograph"
                        {
                            self.imgtask1.image = UIImage(named:"camera")
                        }
                        else if catType == "check-in"
                        {
                            self.imgtask1.image = UIImage(named:"square-pin")
                        }
                        else if catType == "question"
                        {
                            self.imgtask1.image = UIImage(named:"star-1")
                        }
                        
                        
                    }
                    else if i == 1
                    {
                        self.view2.isHidden = false
                        
                        self.lbl2.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        var imgStr = dic.value(forKey: "imagen_prenda") as? String
                        imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                        
                        let imgUrl = URL(string: imgStr as! String)
                        
                        self.imgQuestion.sd_setImage(with: imgUrl)
                        
                        let catType = dic.value(forKey: "categoria_prenda") as? String
                        if catType == "Photograph"  || catType == "photograph"
                        {
                            self.imgtask2.image = UIImage(named:"camera")
                        }
                        else if catType == "check-in"
                        {
                            self.imgtask2.image = UIImage(named:"square-pin")
                        }
                        else if catType == "question"
                        {
                            self.imgtask2.image = UIImage(named:"star-1")
                        }
                        
                        
                    }
                    else if i == 2                    {
                        
                        self.view3.isHidden = false
                        self.lbl3.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        var imgStr = dic.value(forKey: "imagen_prenda") as? String
                        imgStr = imgStr?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                        
                        let imgUrl = URL(string: imgStr as! String)
                        
                        self.imgCheckIn.sd_setImage(with: imgUrl)
                        
                        self.lblCheckIn.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        let catType = dic.value(forKey: "categoria_prenda") as? String
                        if catType == "Photograph"  || catType == "photograph"
                        {
                            self.imgtask3.image = UIImage(named:"camera")
                        }
                        else if catType == "check-in"
                        {
                            self.imgtask3.image = UIImage(named:"square-pin")
                        }
                        else if catType == "question"
                        {
                            self.imgtask3.image = UIImage(named:"star-1")
                        }
                        
                        
                    }
                    else if i == 3
                    {
                        self.view4.isHidden = false
                        self.lbl4.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        let catType = dic.value(forKey: "categoria_prenda") as? String
                        if catType == "Photograph"  || catType == "photograph"
                        {
                            self.imgtask4.image = UIImage(named:"camera")
                        }
                        else if catType == "check-in"
                        {
                            self.imgtask4.image = UIImage(named:"square-pin")
                        }
                        else if catType == "question"
                        {
                            self.imgtask4.image = UIImage(named:"star-1")
                        }
                        
                        
                    }
                    else if i == 4
                    {
                        self.view5.isHidden = false
                        self.lbl5.text = dic.value(forKey: "nombre_prenda") as? String
                        
                        let catType = dic.value(forKey: "categoria_prenda") as? String
                        if catType == "Photograph"  || catType == "photograph"
                        {
                            self.imgtask5.image = UIImage(named:"camera")
                        }
                        else if catType == "check-in"
                        {
                            self.imgtask5.image = UIImage(named:"square-pin")
                        }
                        else if catType == "question"
                        {
                            self.imgtask5.image = UIImage(named:"star-1")
                        }
                        
                        
                    }
                    
                }
                
                let strcode = dicData.value(forKey: "codigo") as! NSString
                let arr = strcode.components(separatedBy: "-") as NSArray
                self.userCode = arr.object(at: 2)  as! NSString
                
                
                /*  if self.arrImgData.count == 3
                 {
                 self.view4.isHidden = true
                 self.view5.isHidden = true
                 
                 self.img4.isHidden = true
                 self.img5.isHidden = true
                 
                 self.lbldot3.isHidden = true
                 self.lbldot4.isHidden = true
                 }
                 else
                 {
                 self.view4.isHidden = false
                 self.view5.isHidden = false
                 
                 self.img4.isHidden = false
                 self.img5.isHidden = false
                 
                 self.lbldot3.isHidden = false
                 self.lbldot4.isHidden = false
                 }*/
                
                if self.userCode == "S"
                {
                    self.view1.isHidden = false
                    self.view2.isHidden = false
                    self.view3.isHidden = false
                    self.view4.isHidden = true
                    self.view5.isHidden = true
                    
                    self.img4.isHidden = true
                    self.img5.isHidden = true
                    
                    self.lbldot3.isHidden = true
                    self.lbldot4.isHidden = true
                }
                else
                {
                    self.view1.isHidden = false
                    self.view2.isHidden = false
                    self.view3.isHidden = false
                    self.view4.isHidden = false
                    self.view5.isHidden = false
                    
                    self.img4.isHidden = false
                    self.img5.isHidden = false
                    
                    self.lbldot3.isHidden = false
                    self.lbldot4.isHidden = false
                }
            }
            else
            {
                print(response.result.error as Any)
                self.Indicator.stopAnimating()
                self.globalMethod.displayAlert(strMessge: "cannot connect to server", globalAlert: self)
            }
        }
        
        
        
    }
    
    
    //MARK:- FACEBOOK METHOD
    
    func sharer(_ sharer: FBSDKSharing!, didCompleteWithResults results: [AnyHashable : Any]!) {
        
        print(results)
        
    }
    func sharerDidCancel(_ sharer: FBSDKSharing!) {
        
        print("cancel sharing")
        
    }
    func sharer(_ sharer: FBSDKSharing!, didFailWithError error: Error!) {
        
        print(error)
        
    }
    
    //MARK:- TEXTFIELD METHOD
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        textField.resignFirstResponder()
        return true
    }
    
    func showAlert()
    {
        let alertController = UIAlertController(title: "CORRECT ANSWER!", message: "TASK COMPLETED", preferredStyle: .alert)
        
        let okAction = UIAlertAction(title: "Ok", style: .default) { (alert) in
            
            self.viewQuestion.isHidden = true
            
        }
        
        alertController.addAction(okAction)
        self.present(alertController, animated:true, completion: nil)
    }
    
}
