//
//  GlobalMethods.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 10/10/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit

class GlobalMethods: NSObject {
    
    public func displayAlert(strMessge: String, globalAlert:UIViewController)
    {
        let alertController = UIAlertController(title: App_Name, message: strMessge, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Ok", style: .cancel, handler: { action in} )
        alertController.addAction(okAction)
        globalAlert.present(alertController, animated:true, completion: nil)
    }
    
    public func isValidEmail(testStr:String) -> Bool
    {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: testStr)
    }
    public func addViewShadw(view:UIView)
    {
        view.layer.masksToBounds = false
        view.layer.cornerRadius = 3.0
        view.layer.shadowOpacity = 0.8
        view.layer.shadowRadius = 3.0
        view.layer.shadowOffset = CGSize(width: 0.0, height: 2.0)
        view.layer.shadowColor = UIColor.gray.cgColor
    }
}
