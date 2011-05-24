#!/usr/bin/env python
# -*- coding: utf-8 -*-


class Actor(MovableObject):
    """ generated source for Actor

    """
    ANIM_DURATION = 100

    def applySpeed(self, d):
        raise NotImplementedError()

    def canJump(self):
        raise NotImplementedError()

    def die(self):
        raise NotImplementedError()

    def getCurrentAnim(self):
        raise NotImplementedError()

    def getFacing(self):
        raise NotImplementedError()

    def getHealth(self):
        raise NotImplementedError()

    def getJumpSpeed(self):
        raise NotImplementedError()

    def getMoveSpeed(self):
        raise NotImplementedError()

    def getXSpeed(self):
        raise NotImplementedError()

    def getYSpeed(self):
        raise NotImplementedError()

    def getZone(self):
        raise NotImplementedError()

    def setJump(self, b):
        raise NotImplementedError()

    def jump(self):
        raise NotImplementedError()

    def setFacing(self, d):
        raise NotImplementedError()

    def resetAccelX(self):
        raise NotImplementedError()


