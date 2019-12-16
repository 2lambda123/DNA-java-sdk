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

package com.github.DNAProject.common;

import com.github.DNAProject.crypto.ECC;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class Common implements AutoCloseable {
    public static String diddna = "did:dna:";
    public static final int MULTI_SIG_MAX_PUBKEY_SIZE = 16;
    public static final int TX_MAX_SIG_SIZE = 16;

    public static byte[] generateKey64Bit() {
        return ECC.generateKey(64);
    }

    public static String currentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private static String now() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

}
