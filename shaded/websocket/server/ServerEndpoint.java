//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import java.lang.annotation.*;
import shaded.websocket.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ServerEndpoint {
    String value();
    
    String[] subprotocols() default {};
    
    Class<? extends Decoder>[] decoders() default {};
    
    Class<? extends Encoder>[] encoders() default {};
    
    Class<? extends ServerEndpointConfig.Configurator> configurator() default ServerEndpointConfig.Configurator.class;
}
