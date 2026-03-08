// Description: Java 25 base object instance implementation for ISOCtryCcy

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

public class CFIntISOCtryCcyObj
	implements ICFIntISOCtryCcyObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFSecISOCtryCcyEditObj edit;
	protected ICFSecSchemaObj schema;
	protected ICFSecISOCtryCcyPKey pKey;
	protected ICFSecISOCtryCcy rec;
	protected ICFSecISOCtryObj requiredContainerCtry;
	protected ICFSecISOCcyObj requiredParentCcy;

	public CFIntISOCtryCcyObj() {
		isNew = true;
		requiredContainerCtry = null;
		requiredParentCcy = null;
	}

	public CFIntISOCtryCcyObj( ICFSecSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredContainerCtry = null;
		requiredParentCcy = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)schema).getISOCtryCcyTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "ISOCtryCcy" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecISOCtryObj scope = getRequiredContainerCtry();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		short val = rec.getRequiredISOCcyId();
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
	public ICFSecISOCtryCcyObj realise() {
		ICFSecISOCtryCcyObj retobj = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().realiseISOCtryCcy(
			(ICFSecISOCtryCcyObj)this );
		return( (ICFSecISOCtryCcyObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().reallyDeepDisposeISOCtryCcy( (ICFSecISOCtryCcyObj)this );
	}

	@Override
	public ICFSecISOCtryCcyObj read() {
		ICFSecISOCtryCcyObj retobj = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().readISOCtryCcyByIdIdx( getPKey().getRequiredISOCtryId(),
			getPKey().getRequiredISOCcyId(), false );
		return( (ICFSecISOCtryCcyObj)retobj );
	}

	@Override
	public ICFSecISOCtryCcyObj read( boolean forceRead ) {
		ICFSecISOCtryCcyObj retobj = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().readISOCtryCcyByIdIdx( getPKey().getRequiredISOCtryId(),
			getPKey().getRequiredISOCcyId(), forceRead );
		return( (ICFSecISOCtryCcyObj)retobj );
	}

	@Override
	public ICFSecISOCtryCcyTableObj getISOCtryCcyTable() {
		return( ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj() );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		schema = value;
	}

	@Override
	public ICFSecISOCtryCcy getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFSecBackingStore().getFactoryISOCtryCcy().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFSecBackingStore().getTableISOCtryCcy().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey().getRequiredISOCtryId(),
						getPKey().getRequiredISOCcyId() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecISOCtryCcy value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFSecISOCtryCcy ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFSecISOCtryCcyRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredContainerCtry = null;
		requiredParentCcy = null;
	}

	@Override
	public ICFSecISOCtryCcy getISOCtryCcyRec() {
		return( (ICFSecISOCtryCcy)getRec() );
	}

	@Override
	public ICFSecISOCtryCcyPKey getPKey() {
		if( pKey == null ) {
			pKey = getSchema().getCFSecBackingStore().getFactoryISOCtryCcy().newPKey();
		}
		return( pKey );
	}

	@Override
	public void setPKey( ICFSecISOCtryCcyPKey value ) {
		if( pKey != value ) {
			pKey = value;
			copyPKeyToRec();
		}
	}

	@Override
	public boolean getIsNew() {
		return( isNew );
	}

	@Override
	public void setIsNew( boolean value ) {
		isNew = value;
	}

	@Override
	public ICFSecISOCtryCcyEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFSecISOCtryCcyObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFSecISOCtryCcyObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().lockISOCtryCcy( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getISOCtryCcyTableObj().newEditInstance( lockobj );
		return( (ICFSecISOCtryCcyEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFSecISOCtryCcyEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFSecISOCtryCcyEditObj getEditAsISOCtryCcy() {
		return( (ICFSecISOCtryCcyEditObj)edit );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecISOCtryCcy rec = getRec();
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
			ICFSecISOCtryCcy rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public short getRequiredISOCtryId() {
		return( getPKey().getRequiredISOCtryId() );
	}

	@Override
	public short getRequiredISOCcyId() {
		return( getPKey().getRequiredISOCcyId() );
	}

	@Override
	public ICFSecISOCtryObj getRequiredContainerCtry() {
		return( getRequiredContainerCtry( false ) );
	}

	@Override
	public ICFSecISOCtryObj getRequiredContainerCtry( boolean forceRead ) {
		if( ( requiredContainerCtry == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerCtry = ((ICFIntSchemaObj)getSchema()).getISOCtryTableObj().readISOCtryByIdIdx( getPKey().getRequiredISOCtryId(), forceRead );
			}
		}
		return( requiredContainerCtry );
	}

	@Override
	public ICFSecISOCcyObj getRequiredParentCcy() {
		return( getRequiredParentCcy( false ) );
	}

	@Override
	public ICFSecISOCcyObj getRequiredParentCcy( boolean forceRead ) {
		if( ( requiredParentCcy == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredParentCcy = ((ICFIntSchemaObj)getSchema()).getISOCcyTableObj().readISOCcyByIdIdx( getPKey().getRequiredISOCcyId(), forceRead );
			}
		}
		return( requiredParentCcy );
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			rec.getPKey().setRequiredContainerCtry(getPKey().getRequiredContainerCtry());
			rec.getPKey().setRequiredParentCcy(getPKey().getRequiredParentCcy());
		}
		if( edit != null ) {
			edit.copyPKeyToRec();
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			getPKey().setRequiredContainerCtry(rec.getPKey().getRequiredContainerCtry());
			getPKey().setRequiredParentCcy(rec.getPKey().getRequiredParentCcy());
		}
	}
}
