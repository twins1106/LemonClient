//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.log4j;

import javax.naming.*;
import java.util.*;
import javax.naming.directory.*;

public enum EmptyJndiContext implements Context, DirContext
{
    INSTANCE;
    
    @Override
    public Object lookup(final Name name) {
        return null;
    }
    
    @Override
    public Object lookup(final String name) {
        return null;
    }
    
    @Override
    public void bind(final Name name, final Object obj) {
    }
    
    @Override
    public void bind(final String name, final Object obj) {
    }
    
    @Override
    public void rebind(final Name name, final Object obj) {
    }
    
    @Override
    public void rebind(final String name, final Object obj) {
    }
    
    @Override
    public void unbind(final Name name) {
    }
    
    @Override
    public void unbind(final String name) {
    }
    
    @Override
    public void rename(final Name oldName, final Name newName) {
    }
    
    @Override
    public void rename(final String oldName, final String newName) {
    }
    
    @Override
    public NamingEnumeration<NameClassPair> list(final Name name) {
        return null;
    }
    
    @Override
    public NamingEnumeration<NameClassPair> list(final String name) {
        return null;
    }
    
    @Override
    public NamingEnumeration<Binding> listBindings(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<Binding> listBindings(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public void destroySubcontext(final Name name) {
    }
    
    @Override
    public void destroySubcontext(final String name) {
    }
    
    @Override
    public Context createSubcontext(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public Context createSubcontext(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public Object lookupLink(final Name name) {
        return null;
    }
    
    @Override
    public Object lookupLink(final String name) {
        return null;
    }
    
    @Override
    public NameParser getNameParser(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public NameParser getNameParser(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public Name composeName(final Name name, final Name prefix) throws NamingException {
        return panic();
    }
    
    @Override
    public String composeName(final String name, final String prefix) throws NamingException {
        return panic();
    }
    
    @Override
    public Object addToEnvironment(final String propName, final Object propVal) {
        return null;
    }
    
    @Override
    public Object removeFromEnvironment(final String propName) {
        return null;
    }
    
    @Override
    public Hashtable<?, ?> getEnvironment() {
        return new Hashtable<Object, Object>();
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String getNameInNamespace() {
        return "";
    }
    
    @Override
    public Attributes getAttributes(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public Attributes getAttributes(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public Attributes getAttributes(final Name name, final String[] attrIds) throws NamingException {
        return panic();
    }
    
    @Override
    public Attributes getAttributes(final String name, final String[] attrIds) throws NamingException {
        return panic();
    }
    
    @Override
    public void modifyAttributes(final Name name, final int mod_op, final Attributes attrs) {
    }
    
    @Override
    public void modifyAttributes(final String name, final int mod_op, final Attributes attrs) {
    }
    
    @Override
    public void modifyAttributes(final Name name, final ModificationItem[] mods) {
    }
    
    @Override
    public void modifyAttributes(final String name, final ModificationItem[] mods) {
    }
    
    @Override
    public void bind(final Name name, final Object obj, final Attributes attrs) {
    }
    
    @Override
    public void bind(final String name, final Object obj, final Attributes attrs) {
    }
    
    @Override
    public void rebind(final Name name, final Object obj, final Attributes attrs) {
    }
    
    @Override
    public void rebind(final String name, final Object obj, final Attributes attrs) {
    }
    
    @Override
    public DirContext createSubcontext(final Name name, final Attributes attrs) throws NamingException {
        return panic();
    }
    
    @Override
    public DirContext createSubcontext(final String name, final Attributes attrs) throws NamingException {
        return panic();
    }
    
    @Override
    public DirContext getSchema(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public DirContext getSchema(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public DirContext getSchemaClassDefinition(final Name name) throws NamingException {
        return panic();
    }
    
    @Override
    public DirContext getSchemaClassDefinition(final String name) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final Name name, final Attributes matchingAttributes, final String[] attributesToReturn) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final String name, final Attributes matchingAttributes, final String[] attributesToReturn) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final Name name, final Attributes matchingAttributes) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final String name, final Attributes matchingAttributes) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final Name name, final String filter, final SearchControls cons) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final String name, final String filter, final SearchControls cons) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final Name name, final String filterExpr, final Object[] filterArgs, final SearchControls cons) throws NamingException {
        return panic();
    }
    
    @Override
    public NamingEnumeration<SearchResult> search(final String name, final String filterExpr, final Object[] filterArgs, final SearchControls cons) throws NamingException {
        return panic();
    }
    
    private static <T> T panic() throws NamingException {
        throw new NamingException("JNDI has been removed");
    }
}
