// Description: Java 25 edit object instance implementation for CFInt License.

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

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;

public class CFIntLicenseEditObj
	implements ICFIntLicenseEditObj
{
	protected ICFIntLicenseObj orig;
	protected ICFIntLicense rec;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFIntTopDomainObj requiredContainerTopDomain;

	public CFIntLicenseEditObj( ICFIntLicenseObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFIntLicense origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerTenant = null;
		requiredContainerTopDomain = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFIntSchemaObj)orig.getSchema()).getLicenseTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "License" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFIntTopDomainObj scope = getRequiredContainerTopDomain();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredName();
		return( objName );
	}

	@Override
	public ICFLibAnyObj getObjQualifier( Class qualifyingClass ) {
		ICFLibAnyObj container = this;
		if( qualifyingClass != null ) {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				else if( qualifyingClass.isInstance( container ) ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		else {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		return( container );
	}

	@Override
	public ICFLibAnyObj getNamedObject( Class qualifyingClass, String objName ) {
		ICFLibAnyObj topContainer = getObjQualifier( qualifyingClass );
		if( topContainer == null ) {
			return( null );
		}
		ICFLibAnyObj namedObject = topContainer.getNamedObject( objName );
		return( namedObject );
	}

	@Override
	public ICFLibAnyObj getNamedObject( String objName ) {
		String nextName;
		String remainingName;
		ICFLibAnyObj subObj = null;
		ICFLibAnyObj retObj;
		int nextDot = objName.indexOf( '.' );
		if( nextDot >= 0 ) {
			nextName = objName.substring( 0, nextDot );
			remainingName = objName.substring( nextDot + 1 );
		}
		else {
			nextName = objName;
			remainingName = null;
		}
		if( remainingName == null ) {
			retObj = subObj;
		}
		else if( subObj == null ) {
			retObj = null;
		}
		else {
			retObj = subObj.getNamedObject( remainingName );
		}
		return( retObj );
	}

	@Override
	public String getObjQualifiedName() {
		String qualName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else if( container instanceof ICFIntTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				qualName = containerName + "." + qualName;
				container = container.getObjScope();
			}
		}
		return( qualName );
	}

	@Override
	public String getObjFullName() {
		String fullName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				fullName = containerName + "." + fullName;
				container = container.getObjScope();
			}
		}
		return( fullName );
	}

	@Override
	public ICFIntLicenseObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFIntLicenseObj retobj = getSchema().getLicenseTableObj().realiseLicense( (ICFIntLicenseObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsLicense().forget();
	}

	@Override
	public ICFIntLicenseObj read() {
		ICFIntLicenseObj retval = getOrigAsLicense().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntLicenseObj read( boolean forceRead ) {
		ICFIntLicenseObj retval = getOrigAsLicense().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntLicenseObj create() {
		copyRecToOrig();
		ICFIntLicenseObj retobj = ((ICFIntSchemaObj)getOrigAsLicense().getSchema()).getLicenseTableObj().createLicense( getOrigAsLicense() );
		if( retobj == getOrigAsLicense() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFIntLicenseEditObj update() {
		getSchema().getLicenseTableObj().updateLicense( (ICFIntLicenseObj)this );
		return( null );
	}

	@Override
	public CFIntLicenseEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getLicenseTableObj().deleteLicense( getOrigAsLicense() );
		return( null );
	}

	@Override
	public ICFIntLicenseTableObj getLicenseTable() {
		return( orig.getSchema().getLicenseTableObj() );
	}

	@Override
	public ICFIntLicenseEditObj getEdit() {
		return( (ICFIntLicenseEditObj)this );
	}

	@Override
	public ICFIntLicenseEditObj getEditAsLicense() {
		return( (ICFIntLicenseEditObj)this );
	}

	@Override
	public ICFIntLicenseEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFIntLicenseObj getOrig() {
		return( orig );
	}

	@Override
	public ICFIntLicenseObj getOrigAsLicense() {
		return( (ICFIntLicenseObj)orig );
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFIntLicense getRec() {
		if( rec == null ) {
			rec = getOrigAsLicense().getSchema().getCFIntBackingStore().getFactoryLicense().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntLicense value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerTenant = null;
			requiredContainerTopDomain = null;
		}
	}

	@Override
	public ICFIntLicense getLicenseRec() {
		return( (ICFIntLicense)getRec() );
	}

	@Override
	public CFLibDbKeyHash256 getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( CFLibDbKeyHash256 value ) {
		orig.setPKey( value );
		copyPKeyToRec();
	}

	@Override
	public boolean getIsNew() {
		return( orig.getIsNew() );
	}

	@Override
	public void setIsNew( boolean value ) {
		orig.setIsNew( value );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredId(CFLibDbKeyHash256 id) {
		if (getPKey() != id) {
			setPKey(id);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getLicenseRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTopDomainId() {
		return( getLicenseRec().getRequiredTopDomainId() );
	}

	@Override
	public String getRequiredName() {
		return( getLicenseRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getLicenseRec().getRequiredName() != value ) {
			getLicenseRec().setRequiredName( value );
		}
	}

	@Override
	public String getOptionalDescription() {
		return( getLicenseRec().getOptionalDescription() );
	}

	@Override
	public void setOptionalDescription( String value ) {
		if( getLicenseRec().getOptionalDescription() != value ) {
			getLicenseRec().setOptionalDescription( value );
		}
	}

	@Override
	public String getOptionalEmbeddedText() {
		return( getLicenseRec().getOptionalEmbeddedText() );
	}

	@Override
	public void setOptionalEmbeddedText( String value ) {
		if( getLicenseRec().getOptionalEmbeddedText() != value ) {
			getLicenseRec().setOptionalEmbeddedText( value );
		}
	}

	@Override
	public String getOptionalFullText() {
		return( getLicenseRec().getOptionalFullText() );
	}

	@Override
	public void setOptionalFullText( String value ) {
		if( getLicenseRec().getOptionalFullText() != value ) {
			getLicenseRec().setOptionalFullText( value );
		}
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant() {
		return( getRequiredOwnerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant( boolean forceRead ) {
		if( forceRead || ( requiredOwnerTenant == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsLicense().getSchema()).getTenantTableObj().readTenantByIdIdx( getLicenseRec().getRequiredTenantId() );
				requiredOwnerTenant = obj;
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public void setRequiredOwnerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getLicenseRec();
		}
		if( value != null ) {
			requiredOwnerTenant = value;
			getLicenseRec().setRequiredOwnerTenant(value.getTenantRec());
		}
		requiredOwnerTenant = value;
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerTopDomain() {
		return( getRequiredContainerTopDomain( false ) );
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerTopDomain( boolean forceRead ) {
		if( forceRead || ( requiredContainerTopDomain == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFIntTopDomainObj obj = ((ICFIntSchemaObj)getOrigAsLicense().getSchema()).getTopDomainTableObj().readTopDomainByIdIdx( getLicenseRec().getRequiredTopDomainId() );
				requiredContainerTopDomain = obj;
				if( obj != null ) {
					requiredContainerTopDomain = obj;
				}
			}
		}
		return( requiredContainerTopDomain );
	}

	@Override
	public void setRequiredContainerTopDomain( ICFIntTopDomainObj value ) {
		if( rec == null ) {
			getLicenseRec();
		}
		if( value != null ) {
			requiredContainerTopDomain = value;
			getLicenseRec().setRequiredContainerTopDomain(value.getTopDomainRec());
		}
		requiredContainerTopDomain = value;
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				rec.setPKey(getPKey());
			}
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				setPKey(rec.getPKey());
			}
		}
	}

	@Override
	public void copyRecToOrig() {
		ICFIntLicense origRec = getOrigAsLicense().getLicenseRec();
		ICFIntLicense myRec = getLicenseRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFIntLicense origRec = getOrigAsLicense().getLicenseRec();
		ICFIntLicense myRec = getLicenseRec();
		myRec.set( origRec );
	}
}
