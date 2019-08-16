//
//  ColorAnnonation.swift
//  NextWhere
//
//  Created by Vinod Tiwari on 07/12/17.
//  Copyright © 2017 Vinod Tiwari. All rights reserved.
//

import UIKit
import MapKit
class ColorAnnonation: MKPointAnnotation {

    var pinColor: UIColor
    
    init(pinColor: UIColor) {
        self.pinColor = pinColor
        super.init()
    }
}
