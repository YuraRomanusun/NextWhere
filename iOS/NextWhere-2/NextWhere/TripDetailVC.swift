//
//  TripDetailVC.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 10/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import Alamofire
class TripDetailVC: UIViewController,EDStarRatingProtocol,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet var mainScroll:MXScrollView!
    @IBOutlet var HeaderView:UIView!
    
    @IBOutlet var Indicator:UIActivityIndicatorView!
    
    @IBOutlet var lblLocation:UILabel!
    
    @IBOutlet var lblHotel:UILabel!
    @IBOutlet var lblHotelAddress:UILabel!
    
    @IBOutlet var lblCheckIn:UILabel!
    @IBOutlet var lblCheckOut:UILabel!
    
    @IBOutlet var viewRating:EDStarRating!
    
    
    @IBOutlet var imgMain:UIImageView!
    @IBOutlet var imgHotel:UIImageView!
    
    var globalMethod = GlobalMethods()
    
    @IBOutlet var lblDepFlightRef:UILabel!
    @IBOutlet var lblDepFlightNo:UILabel!
    @IBOutlet var lblDepFlightDate:UILabel!
    @IBOutlet var lblDepFlightTime:UILabel!
    @IBOutlet var lblDepLeavinigFrom:UILabel!
    @IBOutlet var lblDepArrivingAt:UILabel!
    
    
    @IBOutlet var lblRetFlightRef:UILabel!
    @IBOutlet var lblRetFlightNo:UILabel!
    @IBOutlet var lblRetFlightDate:UILabel!
    @IBOutlet var lblRetFlightTime:UILabel!
    @IBOutlet var lblRetLeavinigFrom:UILabel!
    @IBOutlet var lblRetArrivingAt:UILabel!
    
    
    @IBOutlet var tblTravelar:UITableView!
    
    var arrTravels : NSArray?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        Indicator.hidesWhenStopped = true
        
        mainScroll.parallaxHeader.view = HeaderView
        mainScroll.parallaxHeader.height = 200
        mainScroll.parallaxHeader.mode = MXParallaxHeaderMode.top
        mainScroll.parallaxHeader.minimumHeight = 0
        mainScroll.contentSize = CGSize(width: self.view.frame.size.width, height: 800)
        
        
        viewRating.starImage = UIImage(named: "star")
        viewRating.starHighlightedImage = UIImage(named: "starhighlighted")
        viewRating.maxRating = Int(5.0);
        viewRating.delegate = self
        viewRating.rating = 3.5;
        viewRating.displayMode = UInt(EDStarRatingDisplayHalf);
        viewRating.layoutIfNeeded()
        viewRating.horizontalMargin = 5.0
        
        getHoteldata()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func viewWillAppear(_ animated: Bool) {
        
        self.navigationController?.isNavigationBarHidden = true
        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        if Decodedata != nil
        {
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            
            arrTravels = dicData.value(forKey: "pasajeros") as? NSArray
            
            if arrTravels!.count > 0
            {
                tblTravelar.reloadData()
            }
            
            let location = dicData.value(forKey: "hasta_asignado") as? String
            
            lblLocation.text = location?.uppercased()
            lblHotel.text = dicData.value(forKey: "hotel") as? String
            lblCheckIn.text = dicData.value(forKey: "fecha_desde") as? String
            lblCheckOut.text = dicData.value(forKey: "fecha_hasta") as? String
            
            let myAttribute = [NSAttributedStringKey.font : UIFont(name:"OpenSans", size:9.0)!,NSAttributedStringKey.foregroundColor : UIColor(red: 77.0/2550, green: 77.0/255.0, blue: 77.0/255.0, alpha: 1.0)]
            
            let str1 = NSString(format:"%@-",dicData.value(forKey: "aerolinea_ida") as! String)
            let str2 = NSString(format:"%@",dicData.value(forKey: "numero_vuelo_ida") as! String)
            
            let mystring = NSString(format:"%@%@",str1,str2)
            let range = mystring.range(of: str2 as String)
            let mutaStr = NSMutableAttributedString(string: mystring as String, attributes: nil)
            mutaStr.addAttributes(myAttribute, range: range)
            
            lblDepFlightNo.attributedText = mutaStr
            
            let str3 = NSString(format:"%@-",dicData.value(forKey: "aerolinea_vuelta") as! String)
            let str4 = NSString(format:"%@",dicData.value(forKey: "numero_vuelo_vuelta") as! String)
            
            let mystring1 = NSString(format:"%@%@",str3,str4)
            let range1 = mystring1.range(of: str4 as String)
            let mutaStr1 = NSMutableAttributedString(string: mystring1 as String, attributes: nil)
            mutaStr1.addAttributes(myAttribute, range: range1)
            
            lblRetFlightNo.attributedText = mutaStr1
            
            
            lblDepFlightRef.text = NSString(format:"Ref #%@",dicData.value(forKey: "aerolinea_reference_ida") as! String) as String
            lblRetFlightRef.text = NSString(format:"Ref #%@",dicData.value(forKey: "aerolinea_reference_vuelta") as! String) as String
            
            lblDepFlightDate.text = dicData.value(forKey: "fecha_ida") as? String
            lblRetFlightDate.text = dicData.value(forKey: "fecha_vuelta") as? String
            
            lblDepFlightTime.text = dicData.value(forKey: "hora_ida") as? String
            lblRetFlightTime.text = dicData.value(forKey: "hora_vuelta") as? String
            
            lblDepLeavinigFrom.text = dicData.value(forKey: "aeropuerto_ida") as? String
            lblRetLeavinigFrom.text = dicData.value(forKey: "aeropuerto_vuelta") as? String
            
            lblDepArrivingAt.text = dicData.value(forKey: "aeropuerto_llegada_ida") as? String
            lblRetArrivingAt.text = dicData.value(forKey: "aeropuerto_llegada_vuelta") as? String
            
            let mainImage = dicData.value(forKey: "ruta_imagen_destino") as? String
            if mainImage != nil
            {
                let imgStr = NSString(format:"%@%@",FileDomain,mainImage!) as String
                let imgUrl = URL(string: imgStr)
                imgMain.sd_setImage(with: imgUrl, placeholderImage: UIImage(named:"img_background_my_trip_details"))
                
            }
            
            
        }
    }
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return .lightContent
    }
    //MARK:- IBACTION METHOD
    
    @IBAction func btnBackAction()
    {
        self.navigationController?.popViewController(animated: true)
    }
    
    //MARK:- TABLEVIEW METHOD
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return arrTravels!.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")
        
        if arrTravels!.count > 0
        {
            
            let dic = arrTravels?.object(at: indexPath.row) as! NSDictionary
            let mainview = cell?.contentView.viewWithTag(1) as UIView?
            globalMethod.addViewShadw(view: mainview!)
            
            let lblTitle = cell?.contentView.viewWithTag(3) as! UILabel
            lblTitle.text =  NSString(format:"%@ %@",dic.value(forKey: "nombre") as! String,dic.value(forKey: "apellido") as! String) as String
            
            let lblType = cell?.contentView.viewWithTag(4) as! UILabel
            lblType.text = dic.value(forKey: "nacionalidad")  as? String
            
            
            let imgProfile = cell?.contentView.viewWithTag(2) as! UIImageView
            
            let stGender = dic.value(forKey: "genero") as? NSString
            if stGender == "Masculino"
            {
                imgProfile.image = UIImage(named:"man")
            }
            else
            {
                imgProfile.image = UIImage(named:"lady")
                
            }
            
        }
        
        cell?.selectionStyle = .none
        return cell!
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        return 80
    }
    
    func getHoteldata()
    {
        
        Indicator.startAnimating()
        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
        
        let sId = dicData.value(forKey:"id_hotel") as! NSString
        let  ApiURL = NSString(format:"%@?id=%@",HotelURL,sId) as String
        
        Alamofire.request(ApiURL).responseJSON { (response) in
            
            self.Indicator.stopAnimating()
            
            if let json = response.result.value {
                print("JSON: \(json)")
                
                let dic = json as? NSDictionary
                if dic != nil
                {
                    let hotelImage = dic?.value(forKey: "imagen_hotel") as? String
                    if hotelImage != nil
                    {
                        var imghotelStr = NSString(format:"%@",hotelImage!) as String
                        imghotelStr = imghotelStr.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                        let imghotelUrl = URL(string: imghotelStr)
                        self.imgHotel.sd_setImage(with: imghotelUrl)
                    }
                    
                    self.lblHotelAddress.text = dic?.value(forKey: "direccion") as? String
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
    
}
