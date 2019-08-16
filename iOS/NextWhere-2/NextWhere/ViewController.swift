//
//  ViewController.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 08/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import Alamofire
import AKMaskField

@available(iOS 11.0, *)
class ViewController: UIViewController,UITextFieldDelegate,AKMaskFieldDelegate {
    
    @IBOutlet var txtCode:AKMaskField!
    @IBOutlet var txtEmail:UITextField!
    @IBOutlet var Indicator:UIActivityIndicatorView!
    
    var globalMethod = GlobalMethods()
    let rechable = Reachability()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.isNavigationBarHidden = true
        
        
        self.navigationController?.navigationBar.tintColor = UIColor.black
        
        Indicator.hidesWhenStopped = true
        txtCode.setMask("{dd}-{add}-{a}-{ddd}-{dd}", withMaskTemplate: "XX-XXX-X-XXX-XX")
        txtCode.maskDelegate = self
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK:- IBACTION METHOD
    
    @IBAction func btnLinkAction()
    {
        if (rechable?.isReachable == true)
        {
            if (txtCode.text?.characters.count)! == 0
            {
                globalMethod.displayAlert(strMessge: "Please enter ReservationCode", globalAlert: self)
            }
            else if (txtEmail.text?.characters.count)! == 0
            {
                globalMethod.displayAlert(strMessge: "Please enter ReservationEmail", globalAlert: self)
            }
            else
            {
                Indicator.startAnimating()
                
                
                
                var parameters = [:] as Dictionary<String, String>
                
                parameters = ["code" : txtCode.text!,
                              "email" : txtEmail.text!]
                
                let requestOfAPI = request(LoginURL, method: .post, parameters: parameters, encoding: URLEncoding.default, headers: nil)
                requestOfAPI.responseJSON(completionHandler: { (response) -> Void in
                    debugPrint(response)
                    switch response.result{
                    case .success(let payload):
                        
                        self.Indicator.stopAnimating()
                        
                        
                        if let x = payload as? Dictionary<String,AnyObject>
                        {
                            print(x)
                            
                            let strerr = x["error"] as? NSString
                            if strerr == "1"
                            {
                                
                                self.globalMethod.displayAlert(strMessge: "incorrect login", globalAlert: self)
                            }
                            else
                            {
                                
                                self.txtCode?.resignFirstResponder()
                                self.txtEmail.resignFirstResponder()
                                let EncodedObject = NSKeyedArchiver.archivedData(withRootObject: x)
                                UserDefaults.standard.set(EncodedObject, forKey: "Userdata")
                                UserDefaults.standard.synchronize()
                                
                                
                                UserDefaults.standard.removeObject(forKey: "dic1")
                                UserDefaults.standard.removeObject(forKey: "dic2")
                                UserDefaults.standard.removeObject(forKey: "dic3")
                                UserDefaults.standard.removeObject(forKey: "dic4")
                                UserDefaults.standard.removeObject(forKey: "dic5")
                                UserDefaults.standard.synchronize()
                                
                                self.showHome()
                            }
                            
                        }
                        
                    case .failure(let error):
                        
                        print(error)
                        self.Indicator.stopAnimating()
                        self.globalMethod.displayAlert(strMessge: "cannot connect to server", globalAlert: self)
                    }
                    
                })
                
                
                
            }
        }
        else
        {
            globalMethod.displayAlert(strMessge: "The Internet connection appears to be offline", globalAlert: self)
        }
    }
    
    //MARK:- UITEXTFIELD DELEGATE
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        return true
        
    }
    
    func maskFieldShouldReturn(_ maskField: AKMaskField) -> Bool {
        
        maskField.resignFirstResponder()
        return true
    }
    
    func showHome() {
        
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

    }
    
    
    
}

