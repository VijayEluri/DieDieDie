#!/usr/bin/env python
# -*- coding: utf-8 -*-


class Projectile(MovableObject):
    """ generated source for Projectile

    """

    def updatePosition(self):
        raise NotImplementedError()

    def updateAiming(self, mouseX, mouseY):
        raise NotImplementedError()

    def stop(self):
        raise NotImplementedError()

    def release(self, power):
        raise NotImplementedError()

    def isFlying(self):
        raise NotImplementedError()

    def getAngle(self):
        raise NotImplementedError()

    def getShape(self):
        raise NotImplementedError()

    def getGravityLine(self):
        raise NotImplementedError()

    def getGravity(self):
        raise NotImplementedError()

    def getMaxGravity(self):
        raise NotImplementedError()

    def increaseGravityEffect(self):
        raise NotImplementedError()

    def isGoingDown(self):
        raise NotImplementedError()

    def getEndX(self):
        raise NotImplementedError()

    def getEndY(self):
        raise NotImplementedError()

    def getAirRes(self):
        raise NotImplementedError()

    def calculateEndPos(self):
        raise NotImplementedError()

    def adjustFacingAngle(self):
        raise NotImplementedError()


