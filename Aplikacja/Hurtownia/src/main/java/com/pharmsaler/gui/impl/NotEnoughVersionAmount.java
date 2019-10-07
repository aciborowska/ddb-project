package com.pharmsaler.gui.impl;

import com.pharmsaler.model.Version;

public class NotEnoughVersionAmount extends Exception {

	private Version version;

	public NotEnoughVersionAmount(Version version) {
		this.version = version;
	}

	public Version getVersion() {
		return version;
	}

}
