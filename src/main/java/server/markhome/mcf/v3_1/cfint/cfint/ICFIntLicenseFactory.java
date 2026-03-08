
// Description: Java JPA Factory interface for License.

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfint;

import java.lang.reflect.*;
import java.net.*;
import java.rmi.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/*
 *	ICFIntLicenseFactory interface for License
 */
public interface ICFIntLicenseFactory
{

	/**
	 *	Allocate a LicnTenantIdx key over License instances.
	 *
	 *	@return	The new instance.
	 */
	public ICFIntLicenseByLicnTenantIdxKey newByLicnTenantIdxKey();

	/**
	 *	Allocate a DomainIdx key over License instances.
	 *
	 *	@return	The new instance.
	 */
	public ICFIntLicenseByDomainIdxKey newByDomainIdxKey();

	/**
	 *	Allocate a UNameIdx key over License instances.
	 *
	 *	@return	The new instance.
	 */
	public ICFIntLicenseByUNameIdxKey newByUNameIdxKey();

	/**
	 *	Allocate a License interface implementation.
	 *
	 *	@return	The new instance.
	 */
	public ICFIntLicense newRec();

}
