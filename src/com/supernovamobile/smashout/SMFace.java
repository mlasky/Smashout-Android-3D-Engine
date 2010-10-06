package com.supernovamobile.smashout;

public class SMFace {
    public SMVertex mV1;
    public SMVertex mV2;
    public SMVertex mV3;
    
    public SMVector mNormal;
    
    public SMFace(SMVertex v1, SMVertex v2, SMVertex v3) {
        mV1 = v1;
        mV2 = v2;
        mV3 = v3;
        
        mNormal = SMFace.calcSurfaceNormal(this);
    }
    
    public static SMVector calcSurfaceNormal(SMFace f) {
        SMVector U = SMVector.subtract(f.mV2, f.mV1);
        SMVector V = SMVector.subtract(f.mV3, f.mV1);
        
        float x = (U.mY * V.mZ) - (U.mZ * V.mY);
        float y = (U.mZ * V.mX) - (U.mX * V.mZ);
        float z = (U.mX * V.mY) - (U.mY * V.mX);
        
        return new SMVector(x, y, z);
    }
}