// Description: Java 25 base object instance implementation for SysCluster

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

public class CFIntSysClusterObj
	implements ICFIntSysClusterObj
{
	protected boolean isNew;
	protected ICFSecSysClusterEditObj edit;
	protected ICFSecSchemaObj schema;
	protected Integer pKey;
	protected ICFSecSysCluster rec;
	protected ICFSecClusterObj requiredContainerCluster;

	public CFIntSysClusterObj() {
		isNew = true;
		requiredContainerCluster = null;
	}

	public CFIntSysClusterObj( ICFSecSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredContainerCluster = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)schema).getSysClusterTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SysCluster" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecClusterObj scope = getRequiredContainerCluster();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		int val = rec.getRequiredSingletonId();
		objName = Integer.toString( val );
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
	public ICFSecSysClusterObj realise() {
		ICFSecSysClusterObj retobj = ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().realiseSysCluster(
			(ICFSecSysClusterObj)this );
		return( (ICFSecSysClusterObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().reallyDeepDisposeSysCluster( (ICFSecSysClusterObj)this );
	}

	@Override
	public ICFSecSysClusterObj read() {
		ICFSecSysClusterObj retobj = ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().readSysClusterByIdIdx( getPKey(), false );
		return( (ICFSecSysClusterObj)retobj );
	}

	@Override
	public ICFSecSysClusterObj read( boolean forceRead ) {
		ICFSecSysClusterObj retobj = ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().readSysClusterByIdIdx( getPKey(), forceRead );
		return( (ICFSecSysClusterObj)retobj );
	}

	@Override
	public ICFSecSysClusterTableObj getSysClusterTable() {
		return( ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj() );
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
	public ICFSecSysCluster getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFSecBackingStore().getFactorySysCluster().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFSecBackingStore().getTableSysCluster().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSysCluster value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFSecSysCluster ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFSecSysClusterRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredContainerCluster = null;
	}

	@Override
	public ICFSecSysCluster getSysClusterRec() {
		return( (ICFSecSysCluster)getRec() );
	}

	@Override
	public Integer getPKey() {
		return( pKey );
	}

	@Override
	public void setPKey( Integer value ) {
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
	public ICFSecSysClusterEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFSecSysClusterObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFSecSysClusterObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().lockSysCluster( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getSysClusterTableObj().newEditInstance( lockobj );
		return( (ICFSecSysClusterEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFSecSysClusterEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFSecSysClusterEditObj getEditAsSysCluster() {
		return( (ICFSecSysClusterEditObj)edit );
	}

	@Override
	public int getRequiredSingletonId() {
		return( getPKey() );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster() {
		return( getRequiredContainerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster( boolean forceRead ) {
		if( ( requiredContainerCluster == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerCluster = ((ICFIntSchemaObj)getSchema()).getClusterTableObj().readClusterByIdIdx( getSysClusterRec().getRequiredClusterId(), forceRead );
			}
		}
		return( requiredContainerCluster );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getSysClusterRec().getRequiredClusterId() );
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				rec.setPKey(getPKey());
			}
		}
		if( edit != null ) {
			edit.copyPKeyToRec();
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
}
