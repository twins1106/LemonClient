//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

public class KeyBoardClass
{
    public static int getKeyFromChar(char character) {
        character = Character.toUpperCase(character);
        switch (character) {
            case 'A': {
                return 30;
            }
            case 'B': {
                return 48;
            }
            case 'C': {
                return 46;
            }
            case 'D': {
                return 32;
            }
            case 'E': {
                return 18;
            }
            case 'F': {
                return 33;
            }
            case 'G': {
                return 34;
            }
            case 'H': {
                return 35;
            }
            case 'I': {
                return 23;
            }
            case 'L': {
                return 38;
            }
            case 'M': {
                return 50;
            }
            case 'N': {
                return 49;
            }
            case 'O': {
                return 24;
            }
            case 'P': {
                return 25;
            }
            case 'Q': {
                return 16;
            }
            case 'R': {
                return 19;
            }
            case 'S': {
                return 31;
            }
            case 'T': {
                return 20;
            }
            case 'U': {
                return 22;
            }
            case 'V': {
                return 47;
            }
            case 'W': {
                return 17;
            }
            case 'X': {
                return 45;
            }
            case 'Y': {
                return 21;
            }
            case 'Z': {
                return 44;
            }
            case '0': {
                return 11;
            }
            case '1': {
                return 2;
            }
            case '2': {
                return 3;
            }
            case '3': {
                return 4;
            }
            case '4': {
                return 5;
            }
            case '5': {
                return 6;
            }
            case '6': {
                return 7;
            }
            case '7': {
                return 8;
            }
            case '8': {
                return 9;
            }
            case '9': {
                return 10;
            }
            case '.': {
                return 51;
            }
            case ',': {
                return 146;
            }
            case '\\': {
                return 43;
            }
            case '\'': {
                return 40;
            }
            case '-': {
                return 12;
            }
            default: {
                return 0;
            }
        }
    }
}
