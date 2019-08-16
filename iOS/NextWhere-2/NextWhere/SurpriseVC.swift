//
//  SurpriseVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 22/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit

class SurpriseVC: UIViewController,UIScrollViewDelegate {

    @IBOutlet var scrollmain:UIScrollView!
    @IBOutlet var view1:UIView!
    @IBOutlet var view2:UIView!
    
    @IBOutlet var imgmain1:UIImageView!
    @IBOutlet var imglogo1:UIImageView!
    @IBOutlet var lbldesc1:UILabel!
    @IBOutlet var imgcode1:UIImageView!
    @IBOutlet var lblcode1:UILabel!
    @IBOutlet var lbladdress1:UILabel!
    @IBOutlet var lblterms1:UILabel!

    
    @IBOutlet var imgmain2:UIImageView!
    @IBOutlet var imglogo2:UIImageView!
    @IBOutlet var lbldesc2:UILabel!
    @IBOutlet var imgcode2:UIImageView!
    @IBOutlet var lblcode2:UILabel!
    @IBOutlet var lbladdress2:UILabel!
    @IBOutlet var lblterms2:UILabel!
    
    @IBOutlet var lblSup:UILabel!
    
    
    @IBOutlet var btnClose:UIButton!
    @IBOutlet var btnNext:UIButton!
    @IBOutlet var imgArrow:UIImageView!


    var strTag = NSString()



    override func viewDidLoad() {
        super.viewDidLoad()

      
          self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.font: UIFont(name: "OpenSans", size: 15.0)!]
        
        
         self.navigationController?.navigationBar.tintColor = UIColor.black
         self.navigationItem.title = "View Unlock Surprise"
        
        scrollmain.isPagingEnabled = true
        self.automaticallyAdjustsScrollViewInsets = false
        
        if strTag == "3"
        {
            btnClose.isHidden = true
            btnNext.isHidden = true
            imgArrow.isHidden = true
               lblSup.text = "SURPRISE 1 OF 1"
            setviewFrame(tview: view1, val: 0.0)
            setviewFrame(tview: view2, val: 1.0)
             scrollmain.contentSize = CGSize(width: UIScreen.main.bounds.size.width * 1, height: 0)

        }
        else
        {
            btnClose.isHidden = false
            btnNext.isHidden = false
            imgArrow.isHidden = false
               lblSup.text = "SURPRISE 1 OF 2"
            setviewFrame(tview: view1, val: 0.0)
            setviewFrame(tview: view2, val: 1.0)
             scrollmain.contentSize = CGSize(width: UIScreen.main.bounds.size.width * 2, height: 0)
        }
        
       
       

        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        if Decodedata != nil
        {
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            print(dicData)
            let arr = dicData.value(forKey: "sorpresas") as! NSArray
            if arr.count > 0
            {
                let dic1 = arr.object(at: 0) as? NSDictionary
                let dic2 = arr.object(at: 1) as? NSDictionary
                
                let mainImage1 = dic1?.value(forKey: "imagen") as? String
                if mainImage1 != nil
                {   // strURL = [strURL stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];

                    var imgStr1 = NSString(format:"%@%@",FileDomain,mainImage1!) as String
                    imgStr1 = imgStr1.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                    let imgUrl1 = URL(string: imgStr1)
                    
                    imgmain1.sd_setImage(with: imgUrl1)
                    
                }
                
                let mainImage2 = dic2?.value(forKey: "imagen") as? String
                if mainImage2 != nil
                {
                    var imgStr2 = NSString(format:"%@%@",FileDomain,mainImage2!) as String
                    imgStr2 = imgStr2.addingPercentEncoding(withAllowedCharacters:.urlQueryAllowed)!
                    let imgUrl2 = URL(string: imgStr2)
                    imgmain2.sd_setImage(with: imgUrl2)
                    
                }
                
                let logoImage1 = dic1?.value(forKey: "logo") as? String
                if logoImage1 != nil
                {
                    var logoStr1 = NSString(format:"%@%@",FileDomain,logoImage1!) as String
                    logoStr1 = logoStr1.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!

                    let logoUrl1 = URL(string: logoStr1)
                    imglogo1.sd_setImage(with: logoUrl1)
                    
                }
                
                let logoImage2 = dic2?.value(forKey: "logo") as? String
                if logoImage2 != nil
                {
                    var logoStr2 = NSString(format:"%@%@",FileDomain,logoImage2!) as String
                    logoStr2 = logoStr2.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!

                    let logoUrl2 = URL(string: logoStr2)
                    imglogo2.sd_setImage(with: logoUrl2)
                    
                }
                
                let barImage1 = dic1?.value(forKey: "imagen_barcode") as? String
                if barImage1 != nil
                {
                    var barStr1 = NSString(format:"%@%@",FileDomain,barImage1!) as String
                    barStr1 = barStr1.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!

                    let barUrl1 = URL(string: barStr1)
                    imgcode1.sd_setImage(with: barUrl1)

                    
                }
                
                let barImage2 = dic2?.value(forKey: "imagen_barcode") as? String
                if barImage2 != nil
                {
                    var barStr2 = NSString(format:"%@%@",FileDomain,barImage2!) as String
                    barStr2 = barStr2.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!

                    let barUrl2 = URL(string: barStr2)
                    imgcode2.sd_setImage(with: barUrl2)

                }
                lbldesc1.text = dic1?.value(forKey: "descripcion") as? String
                lbldesc2.text = dic2?.value(forKey: "descripcion") as? String
                
                lblcode1.text = dic1?.value(forKey: "code") as? String
                lblcode2.text = dic2?.value(forKey: "code") as? String
                
                lbladdress1.text = dic1?.value(forKey: "street") as? String
                lbladdress2.text = dic2?.value(forKey: "street") as? String
                
                lblterms1.text = dic1?.value(forKey: "terms") as? String
                lblterms2.text = dic2?.value(forKey: "terms") as? String




               
            }
            
        }
    }
    

    //MARK:-  METHOD
    
    func setviewFrame(tview:UIView,val:CGFloat)
    {
        var frame = tview.frame
        frame.origin.x = val * UIScreen.main.bounds.size.width
        frame.origin.y = 0
        frame.size.width = self.view.frame.size.width
        frame.size.height = scrollmain.frame.size.height
        tview.frame = frame
    }
    
    //MARK:- IBACTION METHOD
    
    @IBAction func btnNextAction(sender:UIButton)
    {
        if strTag == "5"
        {
            scrollmain.contentOffset = CGPoint(x: 1 * UIScreen.main.bounds.size.width, y: 0)
            btnClose.isHidden = false
        }

    }
    
    @IBAction func btnCloseAction(sender:UIButton)
    {
        scrollmain.contentOffset = CGPoint(x: 0 * UIScreen.main.bounds.size.width, y: 0)
         lblSup.text = "SURPRISE 1 OF 2"
        btnClose.isHidden = true

    }
    
    //MARK:- SCROLLVIEW METHOD
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        
        if scrollView == scrollmain
        {
            let pagenumber = scrollmain.contentOffset.x / scrollmain.frame.size.width
            
            if pagenumber == 0
            {
                lblSup.text = "SURPRISE 1 OF 2"
                btnClose.isHidden = true

            }
            else if pagenumber == 1
            {
                lblSup.text = "SURPRISE 2 OF 2"
                btnClose.isHidden = false

            }
           
        }
        
        
        
    }
    

}
