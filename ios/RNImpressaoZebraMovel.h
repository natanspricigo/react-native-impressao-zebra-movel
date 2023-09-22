
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNRNImpressaoZebraMovelSpec.h"

@interface RNImpressaoZebraMovel : NSObject <NativeRNImpressaoZebraMovelSpec>
#else
#import <React/RCTBridgeModule.h>

@interface RNImpressaoZebraMovel : NSObject <RCTBridgeModule>
#endif

@end
