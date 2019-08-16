//
//  WaitMessageVC.swift
//  NextWhere
//
//  Created by Javier Chung on 1/17/18.
//  Copyright © 2018 Vinod Tiwari. All rights reserved.
//

import UIKit

@available(iOS 11.0, *)
class WaitMessageVC: UIViewController {

    @IBOutlet weak var vwHeyName: UIView!
    var views = [UIView]()
    var nextView = 1
    @IBOutlet weak var imLogo: UIImageView!
    
    @IBOutlet weak var btLogout: UIButton!
    @IBOutlet weak var lbName: UILabel!
    @IBOutlet weak var lbMessage1: UILabel!
    @IBOutlet weak var lbMessage2: UILabel!
    @IBOutlet weak var lbMessage3: UILabel!
    @IBOutlet weak var vwClock: UIView!
    @IBOutlet weak var lbClockColon1: UILabel!
    @IBOutlet weak var lbClockColon2: UILabel!
    @IBOutlet weak var lbClockNum1: UILabel!
    @IBOutlet weak var lbClockNum2: UILabel!
    @IBOutlet weak var lbClockNum3: UILabel!
    @IBOutlet weak var lbClockNum4: UILabel!
    @IBOutlet weak var lbClockNum5: UILabel!
    @IBOutlet weak var lbClockNum6: UILabel!
    
    var isBlinkinkg = false
    
    let del = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        views.append(vwHeyName);
        views.append(lbMessage1);
        views.append(lbMessage2);
        views.append(lbMessage3);
        views.append(vwClock);
        
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.isNavigationBarHidden = true
        
        self.navigationController?.navigationBar.tintColor = UIColor.black
        
        btLogout.alpha = 0
        imLogo.alpha = 0
        views.forEach { view in
            view.alpha = 0;
            //view.frame.size.width = self.view.frame.width - 10
            view.frame.origin.x = (self.view.frame.width / 2) - (view.frame.width / 2)
            view.frame.origin.y = (self.view.frame.height / 2) - (view.frame.height / 2) + 50
        }
        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
        
        let dateFrom = dicData.value(forKey:"fecha_desde") as! String
        let hourFrom = dicData.value(forKey:"hora_ida") as! String
        let firstName = dicData.value(forKey:"nombre") as! String
        
        let dateFormatter = DateFormatter()
        let now = Date()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm"
        guard let date = dateFormatter.date(from: dateFrom + " " + hourFrom) else {
            fatalError("ERROR: Date conversion failed due to mismatched format.")
        }
        
        var dateComponent = DateComponents()
        
        dateComponent.day = -2
        
        let endDate = Calendar.current.date(byAdding: dateComponent, to: date)
        
        let components = Calendar.current.dateComponents([.day, .hour, .minute], from: now, to: endDate!)
        
        let days = String(describing: components.day!).map {String($0)}
        let hour = String(describing: components.hour!).map {String($0)}
        let minute = String(describing: components.minute!).map {String($0)}
        
        if days.count == 1 {
            lbClockNum2.text = days[0]
        }
        else if days.count == 2 {
            lbClockNum1.text = days[0]
            lbClockNum2.text = days[1]
        }
        
        if hour.count == 1 {
            lbClockNum4.text = hour[0]
        }
        else if hour.count == 2 {
            lbClockNum3.text = hour[0]
            lbClockNum4.text = hour[1]
        }
        
        if minute.count == 1 {
            lbClockNum6.text = minute[0]
        }
        else if minute.count == 2 {
            lbClockNum5.text = minute[0]
            lbClockNum6.text = minute[1]
        }
        
        lbName.text = firstName
        
        startBlink()
    }
    
    func startBlink() {
        let when = DispatchTime.now() + 1 // change 2 to desired number of seconds
        DispatchQueue.main.asyncAfter(deadline: when) {
            if self.isBlinkinkg {
                self.lbClockColon1.textColor = UIColor(red: 15/255, green: 126/255, blue: 150/255, alpha: 1)
                self.lbClockColon2.textColor = UIColor(red: 15/255, green: 126/255, blue: 150/255, alpha: 1)
            }
            else {
                self.lbClockColon1.textColor = UIColor.clear
                self.lbClockColon2.textColor = UIColor.clear
            }
            self.isBlinkinkg = !self.isBlinkinkg
            self.startBlink()
        }
    }
    
    func showItem(anView: UIView) {
        let currentFrame = self.view.frame
        
        UIView.animate(withDuration: 1.5, animations: {
            anView.frame.origin.y = (currentFrame.height / 2) - (anView.frame.height / 2)
            anView.alpha = 1
        },completion: { (true) in
            let when = DispatchTime.now() + 1.5 // change 2 to desired number of seconds
            DispatchQueue.main.asyncAfter(deadline: when) {
                
                if self.views.count != self.nextView {
                    self.hideItem(anView: anView)
                }
                
                if self.views.count > self.nextView {
                    self.showItem(anView: self.views[self.nextView])
                    self.nextView = self.nextView + 1
                    if self.views.count == self.nextView {
                        UIView.animate(withDuration: 1.5, animations: {
                            self.btLogout.alpha = 1
                            self.imLogo.alpha = 1
                        })
                    }
                }
            }
            
        })
    }
    
    @IBAction func doLogout(_ sender: UIButton) {
        UserDefaults.standard.removeObject(forKey: "Userdata")
        UserDefaults.standard.synchronize()
        del.setRoot()
    }
    
    func hideItem(anView: UIView) {
        let currentFrame = self.view.frame
        
        UIView.animate(withDuration: 1.5, animations: {
            anView.frame.origin.y = (currentFrame.height / 2) - (anView.frame.height / 2)  - 50
            anView.alpha = 0
        })
    }
    
    override func viewDidAppear(_ animated: Bool) {
        self.showItem(anView: vwHeyName)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

}
