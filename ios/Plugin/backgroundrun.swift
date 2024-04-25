import Foundation

@objc public class backgroundrun: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
