/*
 * Copyright (C) 2018 The DNA Authors
 * This file is part of The DNA library.
 *
 *  The DNA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The DNA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with The DNA.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.DNAProject.core.transaction;


public enum AttributeUsage {

	Nonce(0x00),
    Script(0x20),
    DescriptionUrl(0x81),
    Description(0x90),

    ;
    private byte value;

    AttributeUsage(int v) {
        value = (byte)v;
    }

    public byte value() {
        return value;
    }
    
    public static AttributeUsage valueOf(byte v) {
    	for (AttributeUsage e : AttributeUsage.values()) {
    		if (e.value == v) {
    			return e;
    		}
    	}
    	throw new IllegalArgumentException();
    }
}
