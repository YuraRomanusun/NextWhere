 //
 //  CityGuideVC.swift
 //  NextWhere
 //
 //  Created by Vinod Tiwari on 11/10/17.
 //  Copyright © 2017 Vinod Tiwari. All rights reserved.
 //
 
 import UIKit
 import Alamofire
 import MapKit
 @available(iOS 11.0, *)
 class CityGuideVC: UIViewController,UIScrollViewDelegate,UIGestureRecognizerDelegate,UITableViewDelegate,UITableViewDataSource,MKMapViewDelegate{
    
    
    @IBOutlet var scrollTable:UIScrollView!
    @IBOutlet var mainScroll:UIScrollView!
    @IBOutlet var viewcat:UIView!
    
    @IBOutlet var btnarrow:UIButton!
    
    @IBOutlet var viewEat:UIView!
    @IBOutlet var viewDrink:UIView!
    @IBOutlet var viewAttraction:UIView!
    @IBOutlet var viewEvent:UIView!
    
    @IBOutlet var lblLine:UILabel!
    
    @IBOutlet var btnEat:UIButton!
    @IBOutlet var btnEvent:UIButton!
    @IBOutlet var btnDrink:UIButton!
    @IBOutlet var btnAttraction:UIButton!
    
    @IBOutlet var tblEat:UITableView!
    @IBOutlet var tblDrink:UITableView!
    @IBOutlet var tblAttraction:UITableView!
    @IBOutlet var tblEvent:UITableView!
    
    @IBOutlet var Indicator:UIActivityIndicatorView!
    @IBOutlet var mapview:MKMapView!
    
    var lat = CLLocationDegrees()
    var lon = CLLocationDegrees()
    let SelectColor = UIColor(red: 31.0/2550, green: 128.0/255.0, blue: 146.0/255.0, alpha: 1.0)
    let UnSelectColor = UIColor.black
    
    var strTag = NSString()
    
    var arrEat = NSArray()
    var arrDrink = NSArray()
    var arrAttr = NSArray()
    var arrEvent = NSArray()
    var globalMethod = GlobalMethods()
    var lastContentOffset: CGFloat = 0
    var indexPath = -1
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    @objc func rowTapped(gesture: UIGestureRecognizer) {
        print("Row tapped")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.isNavigationBarHidden = true
        
        mainScroll.delegate = self
        
        Indicator.hidesWhenStopped = true
        
        setviewFrame(tview: viewEat, val: 0.0)
        setviewFrame(tview: viewDrink, val: 1.0)
        setviewFrame(tview: viewAttraction, val: 2.0)
        setviewFrame(tview: viewEvent, val: 3.0)
        
        scrollTable.isPagingEnabled = true
        scrollTable.contentSize = CGSize(width: UIScreen.main.bounds.size.width * 4 , height: 0)
        self.automaticallyAdjustsScrollViewInsets = false
        
        setlabelFrame(lbl: lblLine, btn: btnDrink)
        scrollTable.contentOffset = CGPoint(x: 1 * UIScreen.main.bounds.size.width, y: 0)
        
        tblEat.tableFooterView = UIView()
        tblDrink.tableFooterView = UIView()
        tblAttraction.tableFooterView = UIView()
        tblEvent.tableFooterView = UIView()
        
        strTag = "2"
        
        mapview.delegate = self
        
        mapview.bringSubview(toFront: btnarrow)
        
        getData()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
    //MARK:-  METHOD
    
    @objc func clickonHotel()
    {
        
    }
    func setviewFrame(tview:UIView,val:CGFloat)
    {
        var frame = tview.frame
        frame.origin.x = val * UIScreen.main.bounds.size.width
        frame.origin.y = 0
        frame.size.width = self.view.frame.size.width
        frame.size.height = scrollTable.frame.size.height
        tview.frame = frame
    }
    func setHeight(tview:UIView)
    {
        var frame = tview.frame
        frame.size.height = scrollTable.frame.size.height
        tview.frame = frame
    }
    func setlabelFrame(lbl:UILabel,btn:UIButton)
    {
        var frame  = lbl.frame;
        frame.origin.x = btn.frame.origin.x
        frame.size.width = btn.frame.size.width
        lbl.frame = frame
    }
    func setFirstView()
    {
        btnEat.setTitleColor(SelectColor, for: .normal)
        btnDrink.setTitleColor(UnSelectColor, for: .normal)
        btnAttraction.setTitleColor(UnSelectColor, for: .normal)
        btnEvent.setTitleColor(UnSelectColor, for:.normal)
        
        setlabelFrame(lbl: lblLine, btn: btnEat)
        
    }
    func setSecondView()
    {
        btnDrink.setTitleColor(SelectColor, for: .normal)
        btnEat.setTitleColor(UnSelectColor, for: .normal)
        btnAttraction.setTitleColor(UnSelectColor, for: .normal)
        btnEvent.setTitleColor(UnSelectColor, for:.normal)
        
        setlabelFrame(lbl: lblLine, btn: btnDrink)
        
    }
    func setThirdView()
    {
        btnAttraction.setTitleColor(SelectColor, for: .normal)
        btnDrink.setTitleColor(UnSelectColor, for: .normal)
        btnEat.setTitleColor(UnSelectColor, for: .normal)
        btnEvent.setTitleColor(UnSelectColor, for:.normal)
        
        setlabelFrame(lbl: lblLine, btn: btnAttraction)
        
    }
    func setFourthView()
    {
        btnDrink.setTitleColor(SelectColor, for: .normal)
        btnDrink.setTitleColor(UnSelectColor, for: .normal)
        btnAttraction.setTitleColor(UnSelectColor, for: .normal)
        btnEat.setTitleColor(UnSelectColor, for:.normal)
        
        setlabelFrame(lbl: lblLine, btn: btnEvent)
        
    }
    
    
    //MARK:- IBACTION METHOD
    @IBAction func btnBackAction(sender:UIButton)
    {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnHeaderAction(sender:UIButton)
    {
        self.indexPath = -1
        if sender.tag == 1
        {
            strTag = "1"
            scrollTable.contentOffset = CGPoint(x: 0 * UIScreen.main.bounds.size.width, y: 0)
            setFirstView()
            getData()
        }
        else if sender.tag == 2
        {
            strTag = "2"
            
            scrollTable.contentOffset = CGPoint(x: 1 * UIScreen.main.bounds.size.width, y: 0)
            setSecondView()
            getData()
            
        }
        else if sender.tag == 3
        {
            strTag = "3"
            
            scrollTable.contentOffset = CGPoint(x: 2 * UIScreen.main.bounds.size.width, y: 0)
            setThirdView()
            getData()
            
        }
        else if sender.tag == 4
        {
            strTag = "4"
            
            scrollTable.contentOffset = CGPoint(x: 3 * UIScreen.main.bounds.size.width, y: 0)
            setFourthView()
            getData()
            
        }
    }
    @IBAction func btnArrowAction(sender:UIButton)
    {
        if sender.tag == 10
        {
            UIView.animate(withDuration: 0.5, delay: 0.0, options: .curveEaseInOut, animations: {
                
                //self.btnarrow.setImage(UIImage(named:"up"), for: .normal)
                
                sender.tag = 11
                
                var frame = CGRect()
                
                frame = self.mapview.frame
                frame.size.height = self.mapview.frame.size.height + 300
                self.mapview.frame = frame
                
                frame = self.viewcat.frame
                frame.origin.y = self.mapview.frame.origin.y + self.mapview.frame.size.height
                self.viewcat.frame = frame
                
                //                frame = self.btnarrow.frame
                //                frame.origin.y = self.mapview.frame.origin.y + self.mapview.frame.size.height - 37
                //                self.btnarrow.frame = frame
                
                frame = self.scrollTable.frame
                frame.origin.y = self.viewcat.frame.origin.y + self.viewcat.frame.size.height
                frame.size.height = self.scrollTable.frame.size.height - 300
                self.scrollTable.frame = frame
                
                
                
                self.setHeight(tview: self.viewEat)
                self.setHeight(tview: self.viewDrink)
                self.setHeight(tview: self.viewAttraction)
                self.setHeight(tview: self.viewEvent)
                
                
                
            }, completion: nil)
            
            
        }
        else
        {
            
            
            UIView.animate(withDuration: 0.5, delay: 0.0, options: .curveEaseInOut, animations: {
                
                //self.btnarrow.setImage(UIImage(named:"down"), for: .normal)
                
                sender.tag = 10
                var frame = CGRect()
                
                frame = self.mapview.frame
                frame.size.height = self.mapview.frame.size.height - 300
                self.mapview.frame = frame
                
                
                
                frame = self.viewcat.frame
                frame.origin.y = self.mapview.frame.origin.y + self.mapview.frame.size.height
                self.viewcat.frame = frame
                
                //                frame = self.btnarrow.frame
                //                frame.origin.y = self.mapview.frame.origin.y + self.mapview.frame.size.height - 37
                //                self.btnarrow.frame = frame
                
                frame = self.scrollTable.frame
                frame.origin.y = self.viewcat.frame.origin.y + self.viewcat.frame.size.height
                frame.size.height = self.scrollTable.frame.size.height + 300
                self.scrollTable.frame = frame
                
                
                self.setHeight(tview: self.viewEat)
                self.setHeight(tview: self.viewDrink)
                self.setHeight(tview: self.viewAttraction)
                self.setHeight(tview: self.viewEvent)
                
                
                
            }, completion: nil)
        }
    }
    
    @IBAction func btnHotelAction(sender:UIButton)
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
                    
                    let latitute = dic?.value(forKey: "latitud") as? String
                    let longitute = dic?.value(forKey: "longitud") as? String
                    
                    
                    if latitute != nil && longitute != nil
                    {
                        
                        let Ulat:CLLocationDegrees = (CLLocationDegrees)(latitute!)!
                        let Ulon:CLLocationDegrees = (CLLocationDegrees)(longitute!)!
                        
                        if self.mapview.annotations.isEmpty
                        {
                            print("no annoation")
                        }
                        else
                        {        let allAnnotations = self.mapview.annotations
                            self.mapview.removeAnnotations(allAnnotations)
                            
                            
                        }
                        
                        self.mapview.removeOverlays(self.mapview.overlays)
                        
                        
                        let sourceLocation = CLLocationCoordinate2D(latitude:self.delegate.userLat, longitude: self.delegate.userLon)
                        let destinationLocation = CLLocationCoordinate2D(latitude: Ulat, longitude: Ulon)
                        
                        
                        let sourcePlacemark = MKPlacemark(coordinate: sourceLocation, addressDictionary: nil)
                        let destinationPlacemark = MKPlacemark(coordinate: destinationLocation, addressDictionary: nil)
                        
                        
                        let sourceMapItem = MKMapItem(placemark: sourcePlacemark)
                        let destinationMapItem = MKMapItem(placemark: destinationPlacemark)
                        
                        // let sourceAnnotation = MKPointAnnotation()
                        let sourceAnnotation = ColorAnnonation(pinColor: UIColor.green)
                        
                        sourceAnnotation.title = "Your Location"
                        // sourceAnnotation.subtitle = "GET DRIVING DIRECTION"
                        
                        if let location = sourcePlacemark.location {
                            sourceAnnotation.coordinate = location.coordinate
                        }
                        
                        
                        //  let destinationAnnotation = MKPointAnnotation()
                        let destinationAnnotation = ColorAnnonation(pinColor: UIColor.red)
                        
                        destinationAnnotation.title = dic?.value(forKey: "nombreHotel") as? String
                        
                        if let location = destinationPlacemark.location {
                            destinationAnnotation.coordinate = location.coordinate
                        }
                        
                        
                        self.mapview.showAnnotations([sourceAnnotation,destinationAnnotation], animated: true)
                        
                        self.mapview.selectAnnotation(sourceAnnotation, animated: true)
                        let directionRequest = MKDirectionsRequest()
                        directionRequest.source = sourceMapItem
                        directionRequest.destination = destinationMapItem
                        directionRequest.transportType = .automobile
                        
                        let directions = MKDirections(request: directionRequest)
                        
                        directions.calculate { (response, error) in
                            
                            guard let response = response else {
                                if let error = error {
                                    print("Error: \(error)")
                                }
                                
                                return
                                
                            }
                            
                            
                            let route = response.routes[0]
                            self.mapview.add(route.polyline, level: .aboveRoads)
                            
                            let rect = route.polyline.boundingMapRect
                            self.mapview.setRegion(MKCoordinateRegionForMapRect(rect), animated: true)
                            
                            
                        }
                    }
                    else
                    {
                        self.globalMethod.displayAlert(strMessge: "Hotel address not found", globalAlert: self)
                    }
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
    
    //MARK:- TABLEVIEW METHOD
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if strTag == "1"
        {
            return arrEat.count
        }
        else if strTag == "2"
        {
            return arrDrink.count
        }
        else if strTag == "3"
        {
            return arrAttr.count
        }
        else if strTag == "4"
        {
            return arrEvent.count
        }
        
        return 0
        
    }
    
    func refreshUI() {
        DispatchQueue.main.async {
            self.tblAttraction.reloadData()
        }
    }

    func hideCellButton(cell: UITableViewCell, tag: Int) {
        let btn = cell.contentView.viewWithTag(5000) as! UIButton
        let view = cell.contentView.viewWithTag(tag) as! UIView
        view.frame.size.height = 90
        btn.isHidden = true
    }
    
    var jsonButtonLink = [UIControl:String]()
    
    func setCellInkButton(cell: UITableViewCell, dic: NSDictionary?) {
        if dic != nil {
            let btn = cell.contentView.viewWithTag(5000) as! UIButton
            btn.setTitle(dic?.value(forKey: "nombre_link") as! String, for: UIControlState.normal)
            let link = dic?.value(forKey:"link") as! String
            btn.addTarget(self, action: #selector(buttonTapped), for: .touchUpInside)
            jsonButtonLink[btn] = link
        }
    }
    
    @objc func buttonTapped(sender: UIButton) {
        let link = jsonButtonLink[sender] as! String
        
        if link != nil && link != "" {
            UIApplication.shared.openURL(URL(string: link)!)
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell =  UITableViewCell()
        
        if strTag == "1"
        {
            cell = tableView.dequeueReusableCell(withIdentifier: "celleat")!
            
            if arrEat.count > 0
            {
                let dic = arrEat.object(at: indexPath.row) as! NSDictionary
                
                let mainView = cell.contentView.viewWithTag(1001) as UIView?
                mainView?.frame.size.height = 90
                globalMethod.addViewShadw(view: mainView!)
                
                let mainImage = cell.contentView.viewWithTag(1002) as! UIImageView
                
                var strUrl = dic.value(forKey: "imagen") as! String
                strUrl = strUrl.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                let imgUrl = URL(string: strUrl)
                mainImage.sd_setImage(with: imgUrl, placeholderImage: nil)
                
                let lblName  = cell.contentView.viewWithTag(1003) as! UILabel
                let lblType  = cell.contentView.viewWithTag(1004) as! UILabel
                let lblAdd  = cell.contentView.viewWithTag(1005) as! UILabel
                let lblPrice  = cell.contentView.viewWithTag(1006) as! UILabel
                
                lblName.text = dic.value(forKey: "nombre") as? String
                lblType.text = dic.value(forKey: "categoria") as? String
                lblAdd.text = dic.value(forKey: "direccion") as? String
                
                let strPrice =  dic.value(forKey: "rango_precio") as? String
                if strPrice == "Free"
                {
                    lblPrice.text = strPrice
                    
                }
                else
                {
                    lblPrice.text = NSString(format:"$%@",strPrice!) as String
                }
                
                let lblDistance  = cell.contentView.viewWithTag(1007) as! UILabel
                let latitute = dic.value(forKey: "latitud") as? String
                let longitute = dic.value(forKey: "longitud") as? String
                if latitute != nil && longitute != nil
                {
                    let lat1 = (CLLocationDegrees)(latitute!)!
                    let lon1 = (CLLocationDegrees)(longitute!)!
                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                    let loc2 = CLLocation(latitude: delegate.userLat, longitude: delegate.userLon)
                    
                    let distnaceinMeter = loc1.distance(from: loc2)
                    let miles = (distnaceinMeter/1609.344)
                    lblDistance.text = NSString(format:"%.1f mi",miles) as String
                }
                
                if dic.value(forKey: "nombre_link") as! String != "" {
                    setCellInkButton(cell: cell, dic: dic)
                }
                else {
                    hideCellButton(cell: cell, tag: 1001)
                }
                
            }
            else {
                hideCellButton(cell: cell, tag: 1001)
            }
            
            
        }
        else if strTag == "2"
        {
            cell = tableView.dequeueReusableCell(withIdentifier: "celldrink")!
            
            if arrDrink.count > 0
            {
                
                let dic = arrDrink.object(at: indexPath.row) as! NSDictionary
                let mainView = cell.contentView.viewWithTag(2001) as UIView?
                mainView?.frame.size.height = 90
                globalMethod.addViewShadw(view: mainView!)
                
                // let mainImage = cell.contentView.viewWithTag(2002) as! UIImageView
                
                
                let mainImage = cell.contentView.viewWithTag(2002) as! UIImageView
                
                var strUrl = dic.value(forKey: "imagen") as! String
                strUrl = strUrl.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                let imgUrl = URL(string: strUrl)
                mainImage.sd_setImage(with: imgUrl, placeholderImage: nil)
                
                let lblName  = cell.contentView.viewWithTag(2003) as! UILabel
                lblName.text = dic.value(forKey: "nombre") as? String
                let lblType  = cell.contentView.viewWithTag(2004) as! UILabel
                lblType.text = dic.value(forKey: "categoria") as? String
                let lblPrice  = cell.contentView.viewWithTag(2005) as! UILabel
                
                let lblAddress  = cell.contentView.viewWithTag(2007) as! UILabel
                lblAddress.text = dic.value(forKey: "direccion") as? String
                
                let strPrice =  dic.value(forKey: "rango_precio") as? String
                if strPrice == "Free"
                {
                    lblPrice.text = strPrice
                    
                }
                else
                {
                    lblPrice.text = NSString(format:"$%@",strPrice!) as String
                }
                
                let lblDistance  = cell.contentView.viewWithTag(2006) as! UILabel
                let latitute = dic.value(forKey: "latitud") as? String
                let longitute = dic.value(forKey: "longitud") as? String
                if latitute != nil && longitute != nil
                {
                    let lat1 = (CLLocationDegrees)(latitute!)!
                    let lon1 = (CLLocationDegrees)(longitute!)!
                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                    let loc2 = CLLocation(latitude: delegate.userLat, longitude: delegate.userLon)
                    
                    let distnaceinMeter = loc1.distance(from: loc2)
                    let miles = (distnaceinMeter/1609.344)
                    lblDistance.text = NSString(format:"%.1f mi",miles) as String
                }
                
                
                if dic.value(forKey: "nombre_link") as! String != "" {
                    setCellInkButton(cell: cell, dic: dic)
                }
                else {
                    hideCellButton(cell: cell, tag: 2001)
                }
                
            }
            else {
                hideCellButton(cell: cell, tag: 2001)
            }
            
        }
        else if strTag == "3"
        {
            cell = tableView.dequeueReusableCell(withIdentifier: "cellatt")!
            
            if arrAttr.count > 0
            {
                
                let dic = arrAttr.object(at: indexPath.row) as! NSDictionary
                
                let mainView = cell.contentView.viewWithTag(3001) as UIView?
                mainView?.frame.size.height = 90
                globalMethod.addViewShadw(view: mainView!)
                
                let mainImage = cell.contentView.viewWithTag(3002) as! UIImageView
                var strUrl = dic.value(forKey: "imagen") as! String
                strUrl = strUrl.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                let imgUrl = URL(string: strUrl)
                mainImage.sd_setImage(with: imgUrl, placeholderImage: nil)
                
                let lblName  = cell.contentView.viewWithTag(3003) as! UILabel
                let lblType  = cell.contentView.viewWithTag(3004) as! UILabel
                let lblPrice  = cell.contentView.viewWithTag(3005) as! UILabel
                let lblDistance  = cell.contentView.viewWithTag(3006) as! UILabel
                let lblAddress  = cell.contentView.viewWithTag(3007) as! UILabel
                
                lblName.text = dic.value(forKey: "nombre") as? String
                lblType.text = dic.value(forKey: "categoria") as? String
                lblAddress.text = dic.value(forKey: "direccion") as? String
                let strPrice =  dic.value(forKey: "rango_precio") as? String
                if strPrice == "Free"
                {
                    lblPrice.text = strPrice
                    
                }
                else
                {
                    lblPrice.text = NSString(format:"$%@",strPrice!) as String
                }
                
                let latitute = dic.value(forKey: "latitud") as? String
                let longitute = dic.value(forKey: "longitud") as? String
                if latitute != nil && longitute != nil
                {
                    let lat1 = (CLLocationDegrees)(latitute!)!
                    let lon1 = (CLLocationDegrees)(longitute!)!
                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                    let loc2 = CLLocation(latitude: delegate.userLat, longitude: delegate.userLon)
                    
                    let distnaceinMeter = loc1.distance(from: loc2)
                    let miles = (distnaceinMeter/1609.344)
                    lblDistance.text = NSString(format:"%.1f mi",miles) as String
                }
                
                
                if dic.value(forKey: "nombre_link") as! String != "" {
                    setCellInkButton(cell: cell, dic: dic)
                }
                else {
                    hideCellButton(cell: cell, tag: 3001)
                }
                
            }
            else {
                hideCellButton(cell: cell, tag: 3001)
            }
            
        }
        else if strTag == "4"
        {
            cell = tableView.dequeueReusableCell(withIdentifier: "cellevent")!
            
            if arrEvent.count > 0
            {
                let dic = arrEvent.object(at: indexPath.row) as! NSDictionary
                
                let mainView = cell.contentView.viewWithTag(4001) as UIView?
                mainView?.frame.size.height = 90
                globalMethod.addViewShadw(view: mainView!)
                
                let mainImage = cell.contentView.viewWithTag(4002) as! UIImageView
                
                var strUrl = dic.value(forKey: "imagen") as! String
                strUrl = strUrl.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
                
                let imgUrl = URL(string: strUrl)
                mainImage.sd_setImage(with: imgUrl, placeholderImage: nil)
                
                let lblName  = cell.contentView.viewWithTag(4003) as! UILabel
                let lblType  = cell.contentView.viewWithTag(4004) as! UILabel
                let lblAdd  = cell.contentView.viewWithTag(4005) as! UILabel
                let lblPrice  = cell.contentView.viewWithTag(4006) as! UILabel
                
                let lblDistance = cell.contentView.viewWithTag(4007) as! UILabel
                lblName.text = dic.value(forKey: "nombre") as? String
                lblType.text = dic.value(forKey: "categoria") as? String
                lblAdd.text = dic.value(forKey: "direccion") as? String
                let strPrice =  dic.value(forKey: "rango_precio") as? String
                if strPrice == "Free"
                {
                    lblPrice.text = strPrice
                    
                }
                else
                {
                    lblPrice.text = NSString(format:"$%@",strPrice!) as String
                }
                
                let strDis =  dic.value(forKey: "Distance") as? String
                
                lblDistance.text = NSString(format:"%@ mi",strDis!) as String
                
                
                let Dview = cell.contentView.viewWithTag(4008) as UIView?
                globalMethod.addViewShadw(view: Dview!)
                
                let lblDate = cell.contentView.viewWithTag(4009) as! UILabel
                let lblTime = cell.contentView.viewWithTag(4010) as! UILabel
                
                let stDate = dic.value(forKey: "fecha") as? NSString
                let arrDate = stDate?.components(separatedBy: " ")
                lblDate.text = arrDate?[0]
                
                let hour = dic.value(forKey: "hora") as! String
                let Min = dic.value(forKey: "minutos") as! String
                lblTime.text = NSString(format:"%@.%@ HRS",hour,Min) as String
                
                
                
                
                
                //                let latitute = dic.value(forKey: "latitud") as? String
                //                let longitute = dic.value(forKey: "longitud") as? String
                //                if latitute != nil && longitute != nil
                //                {
                //                    let lat1 = (CLLocationDegrees)(latitute!)!
                //                    let lon1 = (CLLocationDegrees)(longitute!)!
                //                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                //                    let loc2 = CLLocation(latitude: delegate.userLat, longitude: delegate.userLon)
                //
                //                    let distnaceinMeter = loc1.distance(from: loc2)
                //                    let miles = (distnaceinMeter/1609.344)
                //                    lblDistance.text = NSString(format:"%.1f mi",miles) as String
                //                }
                
                if dic.value(forKey: "nombre_link") as! String != "" {
                    setCellInkButton(cell: cell, dic: dic)
                }
                else {
                    hideCellButton(cell: cell, tag: 4001)
                }
                
            }
            else {
                hideCellButton(cell: cell, tag: 4001)
            }
        }
        
        cell.selectionStyle = .none
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
//        return UITableViewAutomaticDimension
        
        if strTag == "1" {
            if arrEat.count > 0 {
                let dic = arrEat.object(at: indexPath.row) as! NSDictionary

                if dic.value(forKey: "nombre_link") as! String != "" && self.indexPath == indexPath.row {
                    
                    if let currentCell = tblEat.cellForRow(at: indexPath) {
                        if let view = currentCell.contentView.viewWithTag(1001) {
                            let btn = currentCell.contentView.viewWithTag(5000) as! UIButton
                            view.frame.size.height = 110
                            btn.isHidden = false
                            
                            return 120
                        }
                    }
                    return 100
                    
                }
                else {
                    return 100
                }
            }
        }
        else if strTag == "2" {
            if arrDrink.count > 0 {
                let dic = arrDrink.object(at: indexPath.row) as! NSDictionary

                if dic.value(forKey: "nombre_link") as! String != "" && self.indexPath == indexPath.row {
                    
                    if let currentCell = tblDrink.cellForRow(at: indexPath) {
                        if let view = currentCell.contentView.viewWithTag(2001) {
                            let btn = currentCell.contentView.viewWithTag(5000) as! UIButton
                            view.frame.size.height = 110
                            btn.isHidden = false
                            
                            return 120
                        }
                    }
                    return 100
                    
                }
                else {
                    return 100
                }
            }
        }
        else if strTag == "3" {
            if arrAttr.count > 0 {
                let dic = arrAttr.object(at: indexPath.row) as! NSDictionary

                if dic.value(forKey: "nombre_link") as! String != "" && self.indexPath == indexPath.row {
                    
                    if let currentCell = tblAttraction.cellForRow(at: indexPath) {
                        if let view = currentCell.contentView.viewWithTag(3001) {
                            let btn = currentCell.contentView.viewWithTag(5000) as! UIButton
                            view.frame.size.height = 110
                            btn.isHidden = false
                            
                            return 120
                        }
                    }
                    return 100

                }
                else {
                    return 100
                }
            }
        }
        else if strTag == "4" {
            if arrEvent.count > 0 {
                let dic = arrEvent.object(at: indexPath.row) as! NSDictionary

                if dic.value(forKey: "nombre_link") as! String != "" && self.indexPath == indexPath.row {
                    
                    if let currentCell = tblEvent.cellForRow(at: indexPath) {
                        if let view = currentCell.contentView.viewWithTag(4001) {
                            let btn = currentCell.contentView.viewWithTag(5000) as! UIButton
                            view.frame.size.height = 110
                            btn.isHidden = false
                            
                            return 120
                        }
                        
                    }
                    return 100
                    
                }
                else {
                    return 100
                }
            }
        }

        return 100
        
    }
    
    func tableView(_ tableView: UITableView, didEndDisplaying cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        print("End displaying")
        //refreshUI()
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        let btn = cell.contentView.viewWithTag(5000) as! UIButton
        btn.isHidden = true
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let cell:UITableViewCell = tableView.cellForRow(at: indexPath)!
        
        let currentIndexPath = self.indexPath
        var indexPaths:[IndexPath] = [IndexPath]()
        self.indexPath = indexPath.row
        
        if strTag == "1"
        {
            if arrEat.count > 0
            {
                let dic = arrEat.object(at: indexPath.row) as! NSDictionary
                setmap(dic: dic)
                
                if currentIndexPath > -1 && currentIndexPath != indexPath.row {
                    let prevCell = tblEat.cellForRow(at: IndexPath(row: currentIndexPath, section: 0 ))
                    let dic2 = arrEat.object(at: currentIndexPath) as! NSDictionary
                    if prevCell != nil && indexPath.row != currentIndexPath && dic2.value(forKey: "nombre_link") as! String != "" {
                        hideCellButton(cell: prevCell!, tag: 1001)
                        indexPaths.append(IndexPath(row: currentIndexPath, section: 0))
                    }
                }
                tblEat.reloadRows(at: indexPaths, with: UITableViewRowAnimation.none)
            }
//            tblEat.beginUpdates()
//            tblEat.endUpdates()
        }
        else if strTag == "2"
        {
            if arrDrink.count > 0
            {
                let dic = arrDrink.object(at: indexPath.row) as! NSDictionary
                setmap(dic: dic)
                
                if currentIndexPath > -1 && currentIndexPath != indexPath.row{
                    let prevCell = tblDrink.cellForRow(at: IndexPath(row: currentIndexPath, section: 0 ))
                    let dic2 = arrDrink.object(at: currentIndexPath) as! NSDictionary
                    if prevCell != nil && indexPath.row != currentIndexPath && dic2.value(forKey: "nombre_link") as! String != "" {
                        hideCellButton(cell: prevCell!, tag: 2001)
                        indexPaths.append(IndexPath(row: currentIndexPath, section: 0))
                    }
                }
                tblDrink.reloadRows(at: indexPaths, with: UITableViewRowAnimation.none)
            }


        }
        else if strTag == "3"
        {
            if arrAttr.count > 0
            {
                let dic = arrAttr.object(at: indexPath.row) as! NSDictionary
                setmap(dic: dic)
                
                if currentIndexPath > -1 && currentIndexPath != indexPath.row{
                    let prevCell = tblAttraction.cellForRow(at: IndexPath(row: currentIndexPath, section: 0 ))
                    let dic2 = arrAttr.object(at: currentIndexPath) as! NSDictionary
                    if prevCell != nil && dic2.value(forKey: "nombre_link") as! String != "" {
                        hideCellButton(cell: prevCell!, tag: 3001)
                        indexPaths.append(IndexPath(row: currentIndexPath, section: 0))
                    }
                }
                tblAttraction.reloadRows(at: indexPaths, with: UITableViewRowAnimation.none)
            }
        }
        else if strTag == "4"
        {
            if arrEvent.count > 0
            {
                let dic = arrEvent.object(at: indexPath.row) as! NSDictionary
                setmap(dic: dic)
                
                if currentIndexPath > -1 && currentIndexPath != indexPath.row{
                    let prevCell = tblEvent.cellForRow(at: IndexPath(row: currentIndexPath, section: 0 ))
                    let dic2 = arrEvent.object(at: currentIndexPath) as! NSDictionary
                    if prevCell != nil && indexPath.row != currentIndexPath && dic2.value(forKey: "nombre_link") as! String != "" {
                        hideCellButton(cell: prevCell!, tag: 4001)
                        indexPaths.append(IndexPath(row: currentIndexPath, section: 0))
                    }
                }
                tblEvent.reloadRows(at: indexPaths, with: UITableViewRowAnimation.none)

            }

//            tblEvent.beginUpdates()
//            tblEvent.endUpdates()
        }

        
    }
    //MARK:- SCROLLVIEW METHOD
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        
        if scrollView == scrollTable
        {
            let pagenumber = scrollTable.contentOffset.x / scrollTable.frame.size.width
            
            if pagenumber == 0
            {
                strTag = "1"
                setFirstView()
                getData()
            }
            else if pagenumber == 1
            {
                strTag = "2"
                setSecondView()
                getData()
            }
            else if pagenumber == 2
            {
                strTag = "3"
                setThirdView()
                getData()
            }
            else if pagenumber == 3
            {
                strTag = "4"
                setFourthView()
                getData()
            }
        }
        
        
        
    }
    
    func scrollViewWillBeginDragging(_ scrollView: UIScrollView) {
        
        self.lastContentOffset = scrollView.contentOffset.y
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        if (self.lastContentOffset > scrollView.contentOffset.y)
        {
            print("down")
        }
        else if (self.lastContentOffset < scrollView.contentOffset.y)
        {
            print("up")
            
        }
        
        
    }
    
    //MARK:- API METHOD
    
    func getData()
    {
        Indicator.startAnimating()
        
        var ApiURL = String()
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
        
        let sId = dicData.value(forKey:"id_destino") as! NSString
        if strTag == "1"
        {
            ApiURL = NSString(format:"%@?id=%@",EatURL,sId) as String
        }
        else if strTag == "2"
        {
            ApiURL = NSString(format:"%@?id=%@",DrinkURL,sId) as String
            
        }
        else if strTag == "3"
        {
            ApiURL = NSString(format:"%@?id=%@",AttractionURL,sId) as String
            
        }
        else if strTag == "4"
        {
            ApiURL = NSString(format:"%@?id=%@",EventURL,sId) as String
            
        }
        
        getdata(urlString: ApiURL as NSString)
        
        /*  Alamofire.request(ApiURL, method: .get, parameters: nil, encoding:URLEncoding.methodDependent, headers:nil)
         Alamofire.request(ApiURL).responseJSON { (response) in
         
         self.Indicator.stopAnimating()
         
         if let json = response.result.value {
         print("JSON: \(json)")
         
         if self.strTag == "1"
         {
         self.arrEat = json as! NSArray
         self.tblEat.reloadData()
         }
         else if self.strTag == "2"
         {
         self.arrDrink = json as! NSArray
         self.tblDrink.reloadData()
         
         }
         else if self.strTag == "3"
         {
         self.arrAttr = json as! NSArray
         self.tblAttraction.reloadData()
         }
         else if self.strTag == "4"
         {
         self.arrEvent = json as! NSArray
         self.tblEvent.reloadData()
         /* let arrData = json as! NSMutableArray
         if self.arrEvent.count > 0
         {
         for var i in 0..<self.arrEvent.count
         {
         let dic = (arrData.object(at: i) as! NSMutableDictionary).mutableCopy()
         print(dic)
         let arrnewEvent = NSMutableArray()
         
         let latitute = (dic as AnyObject).value(forKey: "latitud") as? String
         let longitute = (dic as AnyObject).value(forKey: "longitud") as? String
         if latitute != nil && longitute != nil
         {
         let lat1 = (CLLocationDegrees)(latitute!)!
         let lon1 = (CLLocationDegrees)(longitute!)!
         let loc1 = CLLocation(latitude: lat1, longitude:lon1)
         let loc2 = CLLocation(latitude: self.delegate.userLat, longitude: self.delegate.userLon)
         
         let distnaceinMeter = loc1.distance(from: loc2)
         let miles = (distnaceinMeter/1609.344)
         let strMiles = NSString(format:"%.1f",miles) as String
         (dic as AnyObject).setValue(strMiles, forKey: "Distance")
         
         
         arrnewEvent.add(dic)
         }
         
         }
         }*/
         
         }
         
         }
         else
         {
         print(response.result.error as Any)
         self.Indicator.stopAnimating()
         self.globalMethod.displayAlert(strMessge: "cannot connect to server", globalAlert: self)
         }
         }*/
        
        
        
    }
    
    // MARK:- API CALL METHODS
    
    func getdata(urlString:NSString?)
    {
        let sessionConfig = URLSessionConfiguration.default
        
        let session = URLSession(configuration: sessionConfig, delegate: nil, delegateQueue: nil)
        
        let url = NSURL(string: urlString! as String)
        ;
        let urlRequest = NSMutableURLRequest(url: url! as URL)
        
        urlRequest.httpMethod = "GET"
        
        
        
        
        let dataTask = session.dataTask(with: urlRequest as URLRequest) {data,response,error in
            
            DispatchQueue.main.async {
                
                do{
                    
                    
                    let dicjson = try JSONSerialization.jsonObject(with: data!, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSArray
                    print(dicjson)
                    self.Indicator.stopAnimating()
                    
                    if self.strTag == "1"
                    {
                        let arreatdata = NSMutableArray()
                        if dicjson != nil
                        {
                            let arrdata = dicjson?.mutableCopy() as! NSMutableArray
                            for i in 0 ..< arrdata.count
                            {
                                let dic = arrdata.object(at: i) as! NSMutableDictionary
                                
                                
                                let latitute = (dic as AnyObject).value(forKey: "latitud") as? String
                                let longitute = (dic as AnyObject).value(forKey: "longitud") as? String
                                if latitute != nil && longitute != nil && latitute != "" && longitute != ""
                                {
                                    let lat1 = (CLLocationDegrees)(latitute!)!
                                    let lon1 = (CLLocationDegrees)(longitute!)!
                                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                                    let loc2 = CLLocation(latitude: self.delegate.userLat, longitude: self.delegate.userLon)
                                    
                                    let distnaceinMeter = loc2.distance(from: loc1)
                                    let miles = (distnaceinMeter/1609.344)
                                    let strMiles = NSString(format:"%.1f",miles) as String
                                    dic.setValue(strMiles, forKey: "Distance")
                                    
                                    
                                    arreatdata.add(dic)
                                }
                            }
                            
                            print(arreatdata)
                            
                            let descriptor: NSSortDescriptor = NSSortDescriptor(key: "Distance", ascending: true)
                            self.arrEat = arreatdata.sortedArray(using: [descriptor]) as NSArray
                            
                            if self.arrEat.count > 0
                            {
                                print(self.arrEat)
                                self.tblEat.reloadData()
                            }
                            
                        }
                    }
                    else if self.strTag == "2"
                    {
                        let arrdrinkdata = NSMutableArray()
                        if dicjson != nil
                        {
                            let arrdata = dicjson?.mutableCopy() as! NSMutableArray
                            for i in 0 ..< arrdata.count
                            {
                                let dic = arrdata.object(at: i) as! NSMutableDictionary
                                
                                
                                let latitute = (dic as AnyObject).value(forKey: "latitud") as? String
                                let longitute = (dic as AnyObject).value(forKey: "longitud") as? String
                                if latitute != nil && longitute != nil && latitute != "" && longitute != ""
                                {
                                    let lat1 = (CLLocationDegrees)(latitute!)!
                                    let lon1 = (CLLocationDegrees)(longitute!)!
                                    
                                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                                    let loc2 = CLLocation(latitude: self.delegate.userLat, longitude: self.delegate.userLon)

                                    let distnaceinMeter = loc2.distance(from: loc1)
                                    let miles = (distnaceinMeter/1609.344)
                                    let strMiles = NSString(format:"%.1f",miles) as String
                                    dic.setValue(strMiles, forKey: "Distance")

                                    arrdrinkdata.add(dic)
                                }
                            }
                            
                            let descriptor: NSSortDescriptor = NSSortDescriptor(key: "Distance", ascending: true)
                            self.arrDrink = arrdrinkdata.sortedArray(using: [descriptor]) as NSArray
                            
                            if self.arrDrink.count > 0
                            {
                                self.tblDrink.reloadData()
                            }
                            
                        }
                        
                    }
                    else if self.strTag == "3"
                    {
                        let arrattradata = NSMutableArray()
                        if dicjson != nil
                        {
                            let arrdata = dicjson?.mutableCopy() as! NSMutableArray
                            for i in 0 ..< arrdata.count
                            {
                                let dic = arrdata.object(at: i) as! NSMutableDictionary
                                
                                
                                let latitute = (dic as AnyObject).value(forKey: "latitud") as? String
                                let longitute = (dic as AnyObject).value(forKey: "longitud") as? String
                                if latitute != nil && longitute != nil && latitute != "" && longitute != ""
                                {
                                    let lat1 = (CLLocationDegrees)(latitute!)!
                                    let lon1 = (CLLocationDegrees)(longitute!)!
                                    
                                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                                    let loc2 = CLLocation(latitude: self.delegate.userLat, longitude: self.delegate.userLon)
                                    
                                    let distnaceinMeter = loc2.distance(from: loc1)
                                    let miles = (distnaceinMeter/1609.344)
                                    let strMiles = NSString(format:"%.1f",miles) as String
                                    dic.setValue(strMiles, forKey: "Distance")
                                    
                                    
                                    arrattradata.add(dic)
                                }
                            }
                            
                            let descriptor: NSSortDescriptor = NSSortDescriptor(key: "Distance", ascending: true)
                            self.arrAttr = arrattradata.sortedArray(using: [descriptor]) as NSArray
                            
                            if self.arrAttr.count > 0
                            {
                                self.refreshUI()
                                //self.tblAttraction.reloadData()
                            }
                            
                        }
                    }
                    else if self.strTag == "4"
                    {
                        let arreventdata = NSMutableArray()
                        if dicjson != nil
                        {
                            let arrdata = dicjson?.mutableCopy() as! NSMutableArray
                            for i in 0 ..< arrdata.count
                            {
                                let dic = arrdata.object(at: i) as! NSMutableDictionary
                                
                                
                                let latitute = (dic as AnyObject).value(forKey: "latitud") as? String
                                let longitute = (dic as AnyObject).value(forKey: "longitud") as? String
                                if latitute != nil && longitute != nil && latitute != "" && longitute != ""
                                {
                                    let lat1 = (CLLocationDegrees)(latitute!)!
                                    let lon1 = (CLLocationDegrees)(longitute!)!
                                    let loc1 = CLLocation(latitude: lat1, longitude:lon1)
                                    let loc2 = CLLocation(latitude: self.delegate.userLat, longitude: self.delegate.userLon)
                                    
                                    let distnaceinMeter = loc2.distance(from: loc1)
                                    let miles = (distnaceinMeter/1609.344)
                                    let strMiles = NSString(format:"%.1f",miles) as String
                                    dic.setValue(strMiles, forKey: "Distance")
                                    
                                    
                                    
                                    let stdate = dic.value(forKey: "fecha") as! String
                                    let arr = stdate.components(separatedBy: " ") as? NSArray
                                    let Edate = arr?.object(at: 0) as! NSString
                                    //print(Edate)
                                    
                                    
                                    let formatter = DateFormatter()
                                    formatter.dateFormat = "yyyy-MM-dd"
                                    formatter.timeZone = TimeZone(abbreviation: "UTC")
                                    
                                    let eventdate = formatter.date(from: Edate as String)
                                    let fdate = formatter.date(from: self.delegate.user1Date as String)
                                    let sdate = formatter.date(from: self.delegate.user2Date as String)
                                    
                                    
                                    if eventdate?.compare(fdate!) == .orderedAscending || eventdate?.compare(fdate!) == .orderedSame
                                    {
                                        arreventdata.add(dic)
                                        
                                    }
                                    if eventdate?.compare(sdate!) == .orderedAscending || eventdate?.compare(sdate!) == .orderedSame
                                    {
                                        if arreventdata.contains(dic)
                                        {
                                            
                                        }
                                        else
                                        {
                                            arreventdata.add(dic)
                                        }
                                        
                                        
                                    }
                                    
                                }
                                else
                                {
                                    
                                }
                            }
                            
                            let descriptor: NSSortDescriptor = NSSortDescriptor(key: "Distance", ascending: true)
                            self.arrEvent = arreventdata.sortedArray(using: [descriptor]) as NSArray
                            //print(self.arrEvent)
                            if self.arrEvent.count > 0
                            {
                                self.tblEvent.reloadData()
                            }
                            
                        }
                        
                    }
                    
                    
                    
                    
                    
                }catch
                {
                    print(error)
                }
                
                
                
            }
            
        }
        dataTask.resume()
        
    }
    
    func setmap(dic:NSDictionary)
    {
        let latitute = dic.value(forKey: "latitud") as? String
        let longitute = dic.value(forKey: "longitud") as? String
        
        lat = (CLLocationDegrees)(latitute!)!
        lon = (CLLocationDegrees)(longitute!)!
        
        
        if self.mapview.annotations.isEmpty
        {
            print("no annoation")
        }
        else
        {
            let allAnnotations = self.mapview.annotations
            self.mapview.removeAnnotations(allAnnotations)
        }
        
        self.mapview.removeOverlays(self.mapview.overlays)
        
        let sourceLocation = CLLocationCoordinate2D(latitude:delegate.userLat, longitude: delegate.userLon)
        let destinationLocation = CLLocationCoordinate2D(latitude: lat, longitude: lon)
        
        let sourcePlacemark = MKPlacemark(coordinate: sourceLocation, addressDictionary: nil)
        let destinationPlacemark = MKPlacemark(coordinate: destinationLocation, addressDictionary: nil)
        
        let sourceMapItem = MKMapItem(placemark: sourcePlacemark)
        let destinationMapItem = MKMapItem(placemark: destinationPlacemark)
        
        // let sourceAnnotation = MKPointAnnotation()
        let sourceAnnotation = ColorAnnonation(pinColor: UIColor.green)
        sourceAnnotation.title = "Your Location"
        
        if let location = sourcePlacemark.location {
            sourceAnnotation.coordinate = location.coordinate
        }
        
        // let destinationAnnotation = MKPointAnnotation()
        let destinationAnnotation = ColorAnnonation(pinColor: UIColor.red)
        
        destinationAnnotation.title = dic.value(forKey: "nombre") as? String
        
        if let location = destinationPlacemark.location {
            destinationAnnotation.coordinate = location.coordinate
        }
        
        mapview.showAnnotations([sourceAnnotation,destinationAnnotation], animated: true)
        
        mapview.selectAnnotation(destinationAnnotation, animated: true)
        
        let directionRequest = MKDirectionsRequest()
        directionRequest.source = sourceMapItem
        directionRequest.destination = destinationMapItem
        directionRequest.transportType = .automobile
        
        let directions = MKDirections(request: directionRequest)
        
        directions.calculate { (response, error) in
            
            guard let response = response else {
                if let error = error {
                    print("Error: \(error)")
                }
                
                return
                
            }
            
            
            let route = response.routes[0]
            self.mapview.add(route.polyline, level: .aboveRoads)
            
            let rect = route.polyline.boundingMapRect
            self.mapview.setRegion(MKCoordinateRegionForMapRect(rect), animated: true)
            
            
        }
        
        
    }
    
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        
        let renderer = MKPolylineRenderer(overlay: overlay)
        renderer.strokeColor = UIColor.red
        renderer.lineWidth = 4.0
        
        return renderer
        
    }
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        
        
        
        let pinAnn = MKPinAnnotationView(annotation: annotation, reuseIdentifier: "pinVC")
        pinAnn.isDraggable = false
        pinAnn.canShowCallout = true
        pinAnn.animatesDrop = true
        let pointAnno = annotation as! ColorAnnonation
        pinAnn.pinTintColor = pointAnno.pinColor
        pinAnn.rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
        
        let subtitleView = UILabel()
        subtitleView.font = subtitleView.font.withSize(12)
        subtitleView.numberOfLines = 0
        if pinAnn.pinTintColor == UIColor.red
        {
            subtitleView.text = "GET DRIVING DIRECTION"
        }
        subtitleView.textColor = UIColor.red
        pinAnn.detailCalloutAccessoryView = subtitleView
        
        
        
        return pinAnn
        
    }
    
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
        
        let cLat = NSString(format: "%f",self.delegate.userLat)
        let cLon = NSString(format: "%f",self.delegate.userLon)
        
        let ULat = NSString(format: "%f",lat)
        let ULon = NSString(format: "%f",lon)
        
        let urlString = NSString(format: "http://maps.apple.com/?daddr=%@,%@&saddr=%@,%@",ULat,ULon,cLat,cLon)
        
        let stUrl = NSURL(string: urlString as String)
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(stUrl! as URL, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(stUrl! as URL)
        }
        
    }
    
    
    
    
 }
 
 

