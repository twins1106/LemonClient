//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import shaded.websocket.*;
import java.util.*;

public interface ServerEndpointConfig extends EndpointConfig
{
    Class<?> getEndpointClass();
    
    String getPath();
    
    List<String> getSubprotocols();
    
    List<Extension> getExtensions();
    
    Configurator getConfigurator();
    
    public static class Configurator
    {
        private Configurator containerDefaultConfigurator;
        
        static Configurator fetchContainerDefaultConfigurator() {
            final Iterator i$ = ServiceLoader.load(Configurator.class).iterator();
            if (i$.hasNext()) {
                final Configurator impl = i$.next();
                return impl;
            }
            throw new RuntimeException("Cannot load platform configurator");
        }
        
        Configurator getContainerDefaultConfigurator() {
            if (this.containerDefaultConfigurator == null) {
                this.containerDefaultConfigurator = fetchContainerDefaultConfigurator();
            }
            return this.containerDefaultConfigurator;
        }
        
        public String getNegotiatedSubprotocol(final List<String> supported, final List<String> requested) {
            return this.getContainerDefaultConfigurator().getNegotiatedSubprotocol(supported, requested);
        }
        
        public List<Extension> getNegotiatedExtensions(final List<Extension> installed, final List<Extension> requested) {
            return this.getContainerDefaultConfigurator().getNegotiatedExtensions(installed, requested);
        }
        
        public boolean checkOrigin(final String originHeaderValue) {
            return this.getContainerDefaultConfigurator().checkOrigin(originHeaderValue);
        }
        
        public void modifyHandshake(final ServerEndpointConfig sec, final HandshakeRequest request, final HandshakeResponse response) {
        }
        
        public <T> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
            return (T)this.getContainerDefaultConfigurator().getEndpointInstance((Class<Object>)endpointClass);
        }
    }
    
    public static final class Builder
    {
        private String path;
        private Class<?> endpointClass;
        private List<String> subprotocols;
        private List<Extension> extensions;
        private List<Class<? extends Encoder>> encoders;
        private List<Class<? extends Decoder>> decoders;
        private Configurator serverEndpointConfigurator;
        
        public static Builder create(final Class<?> endpointClass, final String path) {
            return new Builder(endpointClass, path);
        }
        
        private Builder() {
            this.subprotocols = Collections.emptyList();
            this.extensions = Collections.emptyList();
            this.encoders = Collections.emptyList();
            this.decoders = Collections.emptyList();
        }
        
        public ServerEndpointConfig build() {
            return (ServerEndpointConfig)new DefaultServerEndpointConfig((Class)this.endpointClass, this.path, (List)Collections.unmodifiableList((List<?>)this.subprotocols), (List)Collections.unmodifiableList((List<?>)this.extensions), (List)Collections.unmodifiableList((List<?>)this.encoders), (List)Collections.unmodifiableList((List<?>)this.decoders), this.serverEndpointConfigurator);
        }
        
        private Builder(final Class endpointClass, final String path) {
            this.subprotocols = Collections.emptyList();
            this.extensions = Collections.emptyList();
            this.encoders = Collections.emptyList();
            this.decoders = Collections.emptyList();
            if (endpointClass == null) {
                throw new IllegalArgumentException("endpointClass cannot be null");
            }
            this.endpointClass = (Class<?>)endpointClass;
            if (path == null || !path.startsWith("/")) {
                throw new IllegalStateException("Path cannot be null and must begin with /");
            }
            this.path = path;
        }
        
        public Builder encoders(final List<Class<? extends Encoder>> encoders) {
            this.encoders = ((encoders == null) ? new ArrayList<Class<? extends Encoder>>() : encoders);
            return this;
        }
        
        public Builder decoders(final List<Class<? extends Decoder>> decoders) {
            this.decoders = ((decoders == null) ? new ArrayList<Class<? extends Decoder>>() : decoders);
            return this;
        }
        
        public Builder subprotocols(final List<String> subprotocols) {
            this.subprotocols = ((subprotocols == null) ? new ArrayList<String>() : subprotocols);
            return this;
        }
        
        public Builder extensions(final List<Extension> extensions) {
            this.extensions = ((extensions == null) ? new ArrayList<Extension>() : extensions);
            return this;
        }
        
        public Builder configurator(final Configurator serverEndpointConfigurator) {
            this.serverEndpointConfigurator = serverEndpointConfigurator;
            return this;
        }
    }
}
