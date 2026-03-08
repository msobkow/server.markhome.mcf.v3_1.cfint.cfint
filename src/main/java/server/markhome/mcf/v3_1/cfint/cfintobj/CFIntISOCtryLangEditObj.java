// Description: Java 25 edit object instance implementation for CFInt ISOCtryLang.

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
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFIntISOCtryLangEditObj
	implements ICFIntISOCtryLangEditObj
{
	protected ICFSecISOCtryLangObj orig;
	protected ICFSecISOCtryLang rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecISOCtryObj requiredContainerCtry;
	protected ICFSecISOLangObj requiredParentLang;

	public CFIntISOCtryLangEditObj( ICFSecISOCtryLangObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecISOCtryLang origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerCtry = null;
		requiredParentLang = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecISOCtryLang rec = getRec();
			createdBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getCreatedByUserId() );
		}
		return( createdBy );
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return( getRec().getCreatedAt() );
	}

	@Override
	public ICFSecSecUserObj getUpdatedBy() {
		if( updatedBy == null ) {
			ICFSecISOCtryLang rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public void setCreatedBy( ICFSecSecUserObj value ) {
		createdBy = value;
		if( value != null ) {
			getRec().setCreatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setCreatedAt( LocalDateTime value ) {
		getRec().setCreatedAt( value );
	}

	@Override
	public void setUpdatedBy( ICFSecSecUserObj value ) {
		updatedBy = value;
		if( value != null ) {
			getRec().setUpdatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setUpdatedAt( LocalDateTime value ) {
		getRec().setUpdatedAt( value );
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)orig.getSchema()).getISOCtryLangTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "ISOCtryLang" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecISOCtryObj scope = getRequiredContainerCtry();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		short val = rec.getRequiredISOLangId();
		objName = Short.toString( val );
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
	public ICFSecISOCtryLangObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecISOCtryLangObj retobj = getSchema().getISOCtryLangTableObj().realiseISOCtryLang( (ICFIntISOCtryLangObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsISOCtryLang().forget();
	}

	@Override
	public ICFSecISOCtryLangObj read() {
		ICFSecISOCtryLangObj retval = getOrigAsISOCtryLang().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOCtryLangObj read( boolean forceRead ) {
		ICFSecISOCtryLangObj retval = getOrigAsISOCtryLang().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOCtryLangObj create() {
		copyRecToOrig();
		ICFSecISOCtryLangObj retobj = ((ICFIntSchemaObj)getOrigAsISOCtryLang().getSchema()).getISOCtryLangTableObj().createISOCtryLang( getOrigAsISOCtryLang() );
		if( retobj == getOrigAsISOCtryLang() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecISOCtryLangEditObj update() {
		getSchema().getISOCtryLangTableObj().updateISOCtryLang( (ICFSecISOCtryLangObj)this );
		return( null );
	}

	@Override
	public CFSecISOCtryLangEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getISOCtryLangTableObj().deleteISOCtryLang( getOrigAsISOCtryLang() );
		return( null );
	}

	@Override
	public ICFSecISOCtryLangTableObj getISOCtryLangTable() {
		return( orig.getSchema().getISOCtryLangTableObj() );
	}

	@Override
	public ICFSecISOCtryLangEditObj getEdit() {
		return( (ICFSecISOCtryLangEditObj)this );
	}

	@Override
	public ICFSecISOCtryLangEditObj getEditAsISOCtryLang() {
		return( (ICFSecISOCtryLangEditObj)this );
	}

	@Override
	public ICFSecISOCtryLangEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecISOCtryLangObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecISOCtryLangObj getOrigAsISOCtryLang() {
		return( (ICFSecISOCtryLangObj)orig );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFSecISOCtryLang getRec() {
		if( rec == null ) {
			rec = getOrigAsISOCtryLang().getSchema().getCFSecBackingStore().getFactoryISOCtryLang().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecISOCtryLang value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerCtry = null;
			requiredParentLang = null;
		}
	}

	@Override
	public ICFSecISOCtryLang getISOCtryLangRec() {
		return( (ICFSecISOCtryLang)getRec() );
	}

	@Override
	public ICFSecISOCtryLangPKey getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( ICFSecISOCtryLangPKey value ) {
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
	public short getRequiredISOCtryId() {
		return( getPKey().getRequiredISOCtryId() );
	}

	@Override
	public short getRequiredISOLangId() {
		return( getPKey().getRequiredISOLangId() );
	}

	@Override
	public ICFSecISOCtryObj getRequiredContainerCtry() {
		return( getRequiredContainerCtry( false ) );
	}

	@Override
	public ICFSecISOCtryObj getRequiredContainerCtry( boolean forceRead ) {
		if( forceRead || ( requiredContainerCtry == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecISOCtryObj obj = ((ICFIntSchemaObj)getOrigAsISOCtryLang().getSchema()).getISOCtryTableObj().readISOCtryByIdIdx( getPKey().getRequiredISOCtryId() );
				requiredContainerCtry = obj;
				if( obj != null ) {
					requiredContainerCtry = obj;
				}
			}
		}
		return( requiredContainerCtry );
	}

	@Override
	public void setRequiredContainerCtry( ICFSecISOCtryObj value ) {
		if( rec == null ) {
			getISOCtryLangRec();
		}
		if( value != null ) {
			requiredContainerCtry = value;
			getISOCtryLangRec().setRequiredContainerCtry(value.getISOCtryRec());
		}
		requiredContainerCtry = value;
	}

	@Override
	public ICFSecISOLangObj getRequiredParentLang() {
		return( getRequiredParentLang( false ) );
	}

	@Override
	public ICFSecISOLangObj getRequiredParentLang( boolean forceRead ) {
		if( forceRead || ( requiredParentLang == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecISOLangObj obj = ((ICFIntSchemaObj)getOrigAsISOCtryLang().getSchema()).getISOLangTableObj().readISOLangByIdIdx( getPKey().getRequiredISOLangId() );
				requiredParentLang = obj;
			}
		}
		return( requiredParentLang );
	}

	@Override
	public void setRequiredParentLang( ICFSecISOLangObj value ) {
		if( rec == null ) {
			getISOCtryLangRec();
		}
		if( value != null ) {
			requiredParentLang = value;
			getISOCtryLangRec().setRequiredParentLang(value.getISOLangRec());
		}
		else {
			requiredParentLang = null;
			getISOCtryLangRec().setRequiredParentLang((ICFSecISOLang)null);
		}
		requiredParentLang = value;
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			rec.getPKey().setRequiredContainerCtry(getPKey().getRequiredContainerCtry());
			rec.getPKey().setRequiredParentLang(getPKey().getRequiredParentLang());
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			getPKey().setRequiredContainerCtry(rec.getPKey().getRequiredContainerCtry());
			getPKey().setRequiredParentLang(rec.getPKey().getRequiredParentLang());
		}
	}

	@Override
	public void copyRecToOrig() {
		ICFSecISOCtryLang origRec = getOrigAsISOCtryLang().getISOCtryLangRec();
		ICFSecISOCtryLang myRec = getISOCtryLangRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecISOCtryLang origRec = getOrigAsISOCtryLang().getISOCtryLangRec();
		ICFSecISOCtryLang myRec = getISOCtryLangRec();
		myRec.set( origRec );
	}
}
