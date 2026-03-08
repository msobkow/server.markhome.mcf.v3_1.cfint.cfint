// Description: Java 25 edit object instance implementation for CFInt SysCluster.

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

public class CFIntSysClusterEditObj
	implements ICFIntSysClusterEditObj
{
	protected ICFSecSysClusterObj orig;
	protected ICFSecSysCluster rec;
	protected ICFSecClusterObj requiredContainerCluster;

	public CFIntSysClusterEditObj( ICFSecSysClusterObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSysCluster origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerCluster = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)orig.getSchema()).getSysClusterTableObj().getClassCode() );
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
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSysClusterObj retobj = getSchema().getSysClusterTableObj().realiseSysCluster( (ICFIntSysClusterObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSysCluster().forget();
	}

	@Override
	public ICFSecSysClusterObj read() {
		ICFSecSysClusterObj retval = getOrigAsSysCluster().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSysClusterObj read( boolean forceRead ) {
		ICFSecSysClusterObj retval = getOrigAsSysCluster().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSysClusterObj create() {
		copyRecToOrig();
		ICFSecSysClusterObj retobj = ((ICFIntSchemaObj)getOrigAsSysCluster().getSchema()).getSysClusterTableObj().createSysCluster( getOrigAsSysCluster() );
		if( retobj == getOrigAsSysCluster() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSysClusterEditObj update() {
		getSchema().getSysClusterTableObj().updateSysCluster( (ICFSecSysClusterObj)this );
		return( null );
	}

	@Override
	public CFSecSysClusterEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSysClusterTableObj().deleteSysCluster( getOrigAsSysCluster() );
		return( null );
	}

	@Override
	public ICFSecSysClusterTableObj getSysClusterTable() {
		return( orig.getSchema().getSysClusterTableObj() );
	}

	@Override
	public ICFSecSysClusterEditObj getEdit() {
		return( (ICFSecSysClusterEditObj)this );
	}

	@Override
	public ICFSecSysClusterEditObj getEditAsSysCluster() {
		return( (ICFSecSysClusterEditObj)this );
	}

	@Override
	public ICFSecSysClusterEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSysClusterObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSysClusterObj getOrigAsSysCluster() {
		return( (ICFSecSysClusterObj)orig );
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
	public ICFSecSysCluster getRec() {
		if( rec == null ) {
			rec = getOrigAsSysCluster().getSchema().getCFSecBackingStore().getFactorySysCluster().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSysCluster value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerCluster = null;
		}
	}

	@Override
	public ICFSecSysCluster getSysClusterRec() {
		return( (ICFSecSysCluster)getRec() );
	}

	@Override
	public Integer getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( Integer value ) {
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
	public int getRequiredSingletonId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSingletonId(int singletonId) {
		if (getPKey() != singletonId) {
			setPKey(singletonId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getSysClusterRec().getRequiredClusterId() );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster() {
		return( getRequiredContainerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster( boolean forceRead ) {
		if( forceRead || ( requiredContainerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsSysCluster().getSchema()).getClusterTableObj().readClusterByIdIdx( getSysClusterRec().getRequiredClusterId() );
				requiredContainerCluster = obj;
				if( obj != null ) {
					requiredContainerCluster = obj;
				}
			}
		}
		return( requiredContainerCluster );
	}

	@Override
	public void setRequiredContainerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getSysClusterRec();
		}
		if( value != null ) {
			requiredContainerCluster = value;
			getSysClusterRec().setRequiredContainerCluster(value.getClusterRec());
		}
		requiredContainerCluster = value;
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
		ICFSecSysCluster origRec = getOrigAsSysCluster().getSysClusterRec();
		ICFSecSysCluster myRec = getSysClusterRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSysCluster origRec = getOrigAsSysCluster().getSysClusterRec();
		ICFSecSysCluster myRec = getSysClusterRec();
		myRec.set( origRec );
	}
}
