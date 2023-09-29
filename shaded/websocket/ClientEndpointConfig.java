//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

public interface ClientEndpointConfig extends EndpointConfig
{
    List<String> getPreferredSubprotocols();
    
    List<Extension> getExtensions();
    
    Configurator getConfigurator();
    
    public static class Configurator
    {
        public void beforeRequest(final Map<String, List<String>> headers) {
        }
        
        public void afterResponse(final HandshakeResponse hr) {
        }
    }
    
    public static final class Builder
    {
        private List<String> preferredSubprotocols;
        private List<Extension> extensions;
        private List<Class<? extends Encoder>> encoders;
        private List<Class<? extends Decoder>> decoders;
        private Configurator clientEndpointConfigurator;
        
        private Builder() {
            this.preferredSubprotocols = Collections.emptyList();
            this.extensions = Collections.emptyList();
            this.encoders = Collections.emptyList();
            this.decoders = Collections.emptyList();
            this.clientEndpointConfigurator = new Configurator() {};
        }
        
        public static Builder create() {
            return new Builder();
        }
        
        public ClientEndpointConfig build() {
            return new DefaultClientEndpointConfig(Collections.unmodifiableList((List<? extends String>)this.preferredSubprotocols), Collections.unmodifiableList((List<? extends Extension>)this.extensions), Collections.unmodifiableList((List<? extends Class<? extends Encoder>>)this.encoders), Collections.unmodifiableList((List<? extends Class<? extends Decoder>>)this.decoders), this.clientEndpointConfigurator);
        }
        
        public Builder configurator(final Configurator clientEndpointConfigurator) {
            this.clientEndpointConfigurator = clientEndpointConfigurator;
            return this;
        }
        
        public Builder preferredSubprotocols(final List<String> preferredSubprotocols) {
            this.preferredSubprotocols = ((preferredSubprotocols == null) ? new ArrayList<String>() : preferredSubprotocols);
            return this;
        }
        
        public Builder extensions(final List<Extension> extensions) {
            this.extensions = ((extensions == null) ? new ArrayList<Extension>() : extensions);
            return this;
        }
        
        public Builder encoders(final List<Class<? extends Encoder>> encoders) {
            this.encoders = ((encoders == null) ? new ArrayList<Class<? extends Encoder>>() : encoders);
            return this;
        }
        
        public Builder decoders(final List<Class<? extends Decoder>> decoders) {
            this.decoders = ((decoders == null) ? new ArrayList<Class<? extends Decoder>>() : decoders);
            return this;
        }
    }
}
