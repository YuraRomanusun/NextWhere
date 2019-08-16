//
//  AppDelegate.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 08/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import ZDCChat
import Alamofire
import CoreLocation
@available(iOS 11.0, *)
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate,CLLocationManagerDelegate {
    
    var window: UIWindow?
    var VideoUrl = NSString()
    var mainview = ViewController()
    
    var userLat = CLLocationDegrees()
    var userLon = CLLocationDegrees()
    
    let locationManager = CLLocationManager()
    
    var user1Date = NSString()
    var user2Date = NSString()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        mainview = self.window?.rootViewController?.storyboard?.instantiateViewController(withIdentifier: "ViewController") as! ViewController
        ZDCChat.initialize(withAccountKey:"59joyKwTxQ0BBCfc2dbQJj6Kn7SM4dWD")
        
        locationManager.delegate = self;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
        
        getData()
        
        return true
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    //MARK:- API METHOD
    
    func getData()
    {
        
        let ApiURL = NSString(format:"%@?id=9",TripURL) as String
        
        Alamofire.request(ApiURL).responseJSON { (response) in
            
            
            if let json = response.result.value {
                print("JSON: \(json)")
                
                let arr = json as? NSArray
                let dic = arr?.object(at: 0) as! NSDictionary
                
                let strurl =  dic.value(forKey: "video") as! NSString
                let arrUrl = strurl.components(separatedBy: "/") as NSArray
                if arrUrl.count > 0
                {
                    self.VideoUrl = arrUrl.object(at: 3) as! NSString
                }
                
            }
            else
            {
                print(response.result.error as Any)
                
            }
        }
        
        
        
    }
    func setRoot()
    {
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        
        if Decodedata != nil
        {
            let homeview = self.window?.rootViewController?.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
            let nav = UINavigationController(rootViewController: homeview)
            self.window?.rootViewController = nav;
        }
        else
        {
            let nav = UINavigationController(rootViewController: mainview)
            self.window?.rootViewController = nav;
            
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        locationManager.stopUpdatingLocation()
        
        if manager.location?.coordinate != nil
        {
            let locValue:CLLocationCoordinate2D = manager.location!.coordinate
            userLat = locValue.latitude
            userLon = locValue.longitude
            
        }
        
        
    }
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        
        print(error)
        print("Location Failed")
    }
    
}

