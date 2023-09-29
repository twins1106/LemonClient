//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import java.util.*;
import shaded.websocket.*;

final class DefaultServerEndpointConfig implements ServerEndpointConfig
{
    private String path;
    private Class<?> endpointClass;
    private List<String> subprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties;
    private Configurator serverEndpointConfigurator;
    
    DefaultServerEndpointConfig(final Class<?> endpointClass, final String path, final List<String> subprotocols, final List<Extension> extensions, final List<Class<? extends Encoder>> encoders, final List<Class<? extends Decoder>> decoders, final Configurator serverEndpointConfigurator) {
        this.userProperties = new HashMap<String, Object>();
        this.path = path;
        this.endpointClass = endpointClass;
        this.subprotocols = Collections.unmodifiableList((List<? extends String>)subprotocols);
        this.extensions = Collections.unmodifiableList((List<? extends Extension>)extensions);
        this.encoders = Collections.unmodifiableList((List<? extends Class<? extends Encoder>>)encoders);
        this.decoders = Collections.unmodifiableList((List<? extends Class<? extends Decoder>>)decoders);
        if (serverEndpointConfigurator == null) {
            this.serverEndpointConfigurator = Configurator.fetchContainerDefaultConfigurator();
        }
        else {
            this.serverEndpointConfigurator = serverEndpointConfigurator;
        }
    }
    
    @Override
    public Class<?> getEndpointClass() {
        return this.endpointClass;
    }
    
    DefaultServerEndpointConfig(final Class<? extends Endpoint> endpointClass, final String path) {
        this.userProperties = new HashMap<String, Object>();
        this.path = path;
        this.endpointClass = endpointClass;
    }
    
    public List<Class<? extends Encoder>> getEncoders() {
        return this.encoders;
    }
    
    public List<Class<? extends Decoder>> getDecoders() {
        return this.decoders;
    }
    
    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public Configurator getConfigurator() {
        return this.serverEndpointConfigurator;
    }
    
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }
    
    @Override
    public final List<String> getSubprotocols() {
        return this.subprotocols;
    }
    
    @Override
    public final List<Extension> getExtensions() {
        return this.extensions;
    }
}
