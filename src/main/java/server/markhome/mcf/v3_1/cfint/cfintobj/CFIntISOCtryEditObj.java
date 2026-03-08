// Description: Java 25 edit object instance implementation for CFInt ISOCtry.

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

public class CFIntISOCtryEditObj
	implements ICFIntISOCtryEditObj
{
	protected ICFSecISOCtryObj orig;
	protected ICFSecISOCtry rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected List<ICFSecISOCtryCcyObj> optionalComponentsCcy;
	protected List<ICFSecISOCtryLangObj> optionalComponentsLang;

	public CFIntISOCtryEditObj( ICFSecISOCtryObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecISOCtry origRec = orig.getRec();
		rec.set( origRec );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecISOCtry rec = getRec();
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
			ICFSecISOCtry rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getISOCtryTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "ISOCtry" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredISOCode();
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
	public ICFSecISOCtryObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecISOCtryObj retobj = getSchema().getISOCtryTableObj().realiseISOCtry( (ICFIntISOCtryObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsISOCtry().forget();
	}

	@Override
	public ICFSecISOCtryObj read() {
		ICFSecISOCtryObj retval = getOrigAsISOCtry().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOCtryObj read( boolean forceRead ) {
		ICFSecISOCtryObj retval = getOrigAsISOCtry().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOCtryObj create() {
		copyRecToOrig();
		ICFSecISOCtryObj retobj = ((ICFIntSchemaObj)getOrigAsISOCtry().getSchema()).getISOCtryTableObj().createISOCtry( getOrigAsISOCtry() );
		if( retobj == getOrigAsISOCtry() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecISOCtryEditObj update() {
		getSchema().getISOCtryTableObj().updateISOCtry( (ICFSecISOCtryObj)this );
		return( null );
	}

	@Override
	public CFSecISOCtryEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getISOCtryTableObj().deleteISOCtry( getOrigAsISOCtry() );
		return( null );
	}

	@Override
	public ICFSecISOCtryTableObj getISOCtryTable() {
		return( orig.getSchema().getISOCtryTableObj() );
	}

	@Override
	public ICFSecISOCtryEditObj getEdit() {
		return( (ICFSecISOCtryEditObj)this );
	}

	@Override
	public ICFSecISOCtryEditObj getEditAsISOCtry() {
		return( (ICFSecISOCtryEditObj)this );
	}

	@Override
	public ICFSecISOCtryEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecISOCtryObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecISOCtryObj getOrigAsISOCtry() {
		return( (ICFSecISOCtryObj)orig );
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
	public ICFSecISOCtry getRec() {
		if( rec == null ) {
			rec = getOrigAsISOCtry().getSchema().getCFSecBackingStore().getFactoryISOCtry().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecISOCtry value ) {
		if( rec != value ) {
			rec = value;
		}
	}

	@Override
	public ICFSecISOCtry getISOCtryRec() {
		return( (ICFSecISOCtry)getRec() );
	}

	@Override
	public Short getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( Short value ) {
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
		return( getPKey() );
	}

	@Override
	public void setRequiredISOCtryId(short iSOCtryId) {
		if (getPKey() != iSOCtryId) {
			setPKey(iSOCtryId);
			optionalComponentsCcy = null;
			optionalComponentsLang = null;
		}
	}

	@Override
	public String getRequiredISOCode() {
		return( getISOCtryRec().getRequiredISOCode() );
	}

	@Override
	public void setRequiredISOCode( String value ) {
		if( getISOCtryRec().getRequiredISOCode() != value ) {
			getISOCtryRec().setRequiredISOCode( value );
		}
	}

	@Override
	public String getRequiredName() {
		return( getISOCtryRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getISOCtryRec().getRequiredName() != value ) {
			getISOCtryRec().setRequiredName( value );
		}
	}

	@Override
	public List<ICFSecISOCtryCcyObj> getOptionalComponentsCcy() {
		List<ICFSecISOCtryCcyObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().readISOCtryCcyByCtryIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> getOptionalComponentsCcy( boolean forceRead ) {
		List<ICFSecISOCtryCcyObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().readISOCtryCcyByCtryIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecISOCtryLangObj> getOptionalComponentsLang() {
		List<ICFSecISOCtryLangObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getISOCtryLangTableObj().readISOCtryLangByCtryIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecISOCtryLangObj> getOptionalComponentsLang( boolean forceRead ) {
		List<ICFSecISOCtryLangObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getISOCtryLangTableObj().readISOCtryLangByCtryIdx( getPKey(),
			forceRead );
		return( retval );
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
		ICFSecISOCtry origRec = getOrigAsISOCtry().getISOCtryRec();
		ICFSecISOCtry myRec = getISOCtryRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecISOCtry origRec = getOrigAsISOCtry().getISOCtryRec();
		ICFSecISOCtry myRec = getISOCtryRec();
		myRec.set( origRec );
	}
}
