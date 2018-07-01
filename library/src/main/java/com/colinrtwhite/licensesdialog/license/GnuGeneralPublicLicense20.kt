package com.colinrtwhite.licensesdialog.license

import com.colinrtwhite.licensesdialog.model.License


object GnuGeneralPublicLicense20 : License {

	override val title = "GNU General Public License 2.0"

	override val text = run {
		"This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.\n" +
		"\n" +
		"This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.\n" +
		"\n" +
		"You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA"
	}

	override val copyrightPrefix = "Copyright (C)"
}