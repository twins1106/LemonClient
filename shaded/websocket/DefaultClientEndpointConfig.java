//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

final class DefaultClientEndpointConfig implements ClientEndpointConfig
{
    private List<String> preferredSubprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties;
    private ClientEndpointConfig.Configurator clientEndpointConfigurator;
    
    DefaultClientEndpointConfig(final List<String> preferredSubprotocols, final List<Extension> extensions, final List<Class<? extends Encoder>> encoders, final List<Class<? extends Decoder>> decoders, final ClientEndpointConfig.Configurator clientEndpointConfigurator) {
        this.userProperties = new HashMap<String, Object>();
        this.preferredSubprotocols = Collections.unmodifiableList((List<? extends String>)preferredSubprotocols);
        this.extensions = Collections.unmodifiableList((List<? extends Extension>)extensions);
        this.encoders = Collections.unmodifiableList((List<? extends Class<? extends Encoder>>)encoders);
        this.decoders = Collections.unmodifiableList((List<? extends Class<? extends Decoder>>)decoders);
        this.clientEndpointConfigurator = clientEndpointConfigurator;
    }
    
    public List<String> getPreferredSubprotocols() {
        return this.preferredSubprotocols;
    }
    
    public List<Extension> getExtensions() {
        return this.extensions;
    }
    
    public List<Class<? extends Encoder>> getEncoders() {
        return this.encoders;
    }
    
    public List<Class<? extends Decoder>> getDecoders() {
        return this.decoders;
    }
    
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }
    
    public ClientEndpointConfig.Configurator getConfigurator() {
        return this.clientEndpointConfigurator;
    }
}
