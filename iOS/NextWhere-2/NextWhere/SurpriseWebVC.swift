//
//  SurpriseWebVC.swift
//  NextWhere
//
//  Created by pingchoi on 2/7/18.
//  Copyright © 2018 Vinod Tiwari. All rights reserved.
//

import Foundation
import WebKit

class SurpriseWebVC: UIViewController, WKNavigationDelegate, WKUIDelegate{
    
    @IBOutlet var webView:WKWebView!
    @IBOutlet var activityIndicator:UIActivityIndicatorView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.font: UIFont(name: "OpenSans", size: 15.0)!]
        
        
        self.navigationController?.navigationBar.tintColor = UIColor.black
        self.navigationItem.title = "View Unlock Surprise"
        
        webView.navigationDelegate = self
        webView.uiDelegate = self
        
        var myURL = URL(string: "http://next-where.com/webapp/index.php")
        
        
        let Decodedata = UserDefaults.standard.object(forKey: "Userdata")
        if Decodedata != nil
        {
            let dicData = NSKeyedUnarchiver.unarchiveObject(with: Decodedata as! Data) as! NSDictionary
            let code = dicData.value(forKey: "codigo") as! String
           
            let arr = dicData.value(forKey: "pasajeros") as! NSArray
            if arr.count > 0
            {
                let dic1 = arr.object(at: 0) as? NSDictionary
                if dic1 != nil
                {
                    let userid = dic1?.value(forKey: "id_usuario") as! String
                    
                    if code != "" && userid != ""
                    {
                        let codeArray = Array(code)
                        var urlstring = "http://next-where.com/webapp/index.php"
                        if codeArray[7] == "M" || codeArray[7] == "H"
                        {
                            urlstring += "?mode=hunt&id=" + userid
                        }
                        else if codeArray[7] == "S"
                        {
                            urlstring += "?mode=search&id=" + userid
                        }
                        print(urlstring)
                        myURL = URL(string: urlstring)
                    }
                }
            }
        }
        
        
        let myRequest = URLRequest(url:myURL!)
        
        activityIndicator.hidesWhenStopped = true
        activityIndicator.center = self.view.center
        activityIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        activityIndicator.startAnimating()
        
        webView.load(myRequest)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
    }
    
    func showActivityIndicator(show: Bool) {
        if show {
            activityIndicator.startAnimating()
        } else {
            activityIndicator.stopAnimating()
        }
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        showActivityIndicator(show: false)
    }
    
    func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        showActivityIndicator(show: true)
    }
    
    func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
        showActivityIndicator(show: false)
    }
}
